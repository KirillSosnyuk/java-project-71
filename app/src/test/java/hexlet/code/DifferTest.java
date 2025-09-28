package hexlet.code;

import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertEquals;

class DifferTest {

    @Test
    public void testGenerateDifferentFormats() throws Exception {
        var expected = "{\n"
                + "  - follow: false\n"
                + "    host: hexlet.io\n"
                + "  - proxy: 123.234.53.22\n"
                + "  - timeout: 50\n"
                + "  + timeout: 20\n"
                + "  + verbose: true\n"
                + "}";
        var actual = Differ.generate("./src/test/resources/first.json", "./src/test/resources/second.yaml");
        assertEquals(expected, actual);
    }

    @Test
    public void testGenerateSameFile() throws Exception {
        var expected = "{\n"
                + "    host: hexlet.io\n"
                + "    timeout: 20\n"
                + "    verbose: true\n"
                + "}";
        var actual = Differ.generate("./src/test/resources/second.json", "./src/test/resources/second.json");
        assertEquals(expected, actual);
    }

    @Test
    public void testGenerateOverwise() throws Exception {
        var expected = "{\n"
                + "  + follow: false\n"
                + "    host: hexlet.io\n"
                + "  + proxy: 123.234.53.22\n"
                + "  - timeout: 20\n"
                + "  + timeout: 50\n"
                + "  - verbose: true\n"
                + "}";
        var actual = Differ.generate("./src/test/resources/second.json", "./src/test/resources/first.yaml");
        assertEquals(expected, actual);
    }
    @Test
    public void testPlainStyle() throws Exception {
        var expected = "Property 'chars2' was updated. From [complex value] to false\n"
                + "Property 'checked' was updated. From false to true\n"
                + "Property 'default' was updated. From null to [complex value]\n"
                + "Property 'id' was updated. From 45 to null\n"
                + "Property 'key1' was removed\n"
                + "Property 'key2' was added with value: 'value2'\n"
                + "Property 'numbers2' was updated. From [complex value] to [complex value]\n"
                + "Property 'numbers3' was removed\n"
                + "Property 'numbers4' was added with value: [complex value]\n"
                + "Property 'obj1' was added with value: [complex value]\n"
                + "Property 'setting1' was updated. From 'Some value' to 'Another value'\n"
                + "Property 'setting2' was updated. From 200 to 300\n"
                + "Property 'setting3' was updated. From true to 'none'";
        var actual = Differ.generate("./src/test/resources/file1.yaml",
                "./src/test/resources/file2.json",
                "plain");
        assertEquals(expected, actual);
    }

    @Test
    public void testJsonStyle() throws Exception {

        var expected = "[{\"key\":\"chars1\",\"previousValue\":[\"a\",\"b\",\"c\"],"
                + "\"currentValue\":[\"a\",\"b\",\"c\"],"
                + "\"status\":\"unchanged\"},"
                + "{\"key\":\"chars2\",\"previousValue\":[\"d\",\"e\",\"f\"],"
                + "\"currentValue\":false,\"status\":\"updated\"},{\"key\":\"checked\","
                + "\"previousValue\":false,\"currentValue\":true,\"status\":\"updated\"},"
                + "{\"key\":\"default\",\"previousValue\":null,\"currentValue\":[\"value1\",\"value2\"],"
                + "\"status\":\"updated\"},{\"key\":\"id\",\"previousValue\":45,\"currentValue\":null,"
                + "\"status\":\"updated\"},{\"key\":\"key1\",\"previousValue\":\"value1\",\"currentValue\":null,"
                + "\"status\":\"removed\"},{\"key\":\"key2\",\"previousValue\":null,\"currentValue\":\"value2\","
                + "\"status\":\"added\"},{\"key\":\"numbers1\",\"previousValue\":[1,2,3,4],\"currentValue\":[1,2,3,4],"
                + "\"status\":\"unchanged\"},{\"key\":\"numbers2\",\"previousValue\":[2,3,4,5],"
                + "\"currentValue\":[22,33,44,55],"
                + "\"status\":\"updated\"},{\"key\":\"numbers3\",\"previousValue\":[3,4,5],\"currentValue\":null,"
                + "\"status\":\"removed\"},{\"key\":\"numbers4\",\"previousValue\":null,\"currentValue\":[4,5,6],"
                + "\"status\":\"added\"},{\"key\":\"obj1\","
                + "\"previousValue\":null,\"currentValue\":{\"nestedKey\":\"value\","
                + "\"isNested\":true},\"status\":\"added\"},{\"key\":\"setting1\",\"previousValue\":\"Some value\","
                + "\"currentValue\":\"Another value\",\"status\":\"updated\"},{\"key\":\"setting2\","
                + "\"previousValue\":200,\"currentValue\":300,\"status\":\"updated\"},{\"key\":\"setting3\","
                + "\"previousValue\":true,\"currentValue\":\"none\",\"status\":\"updated\"}]";
        var actual = Differ.generate("./src/test/resources/file1.yaml",
                "./src/test/resources/file2.json",
                "json");
        assertEquals(expected, actual);
    }

}
