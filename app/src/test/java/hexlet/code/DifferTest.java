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

}
