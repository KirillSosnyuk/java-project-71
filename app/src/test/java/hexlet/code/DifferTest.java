package hexlet.code;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;


import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DifferTest {
    private static Path getFixturePath(String fileName) {
        return Paths
                .get("src", "test", "resources", fileName)
                .toAbsolutePath()
                .normalize();
    }

    private static String readFixture(String fileName) throws Exception {
        var fixturePath = getFixturePath(fileName);
        return Files.readString(fixturePath).trim();
    }

    private static Stream<Arguments> filesForTest() {
        return Stream.of(
                Arguments.of("file1.json", "file2.json", "normalDiff.txt", "stylish"),
                Arguments.of("file1.json", "file2.json", "plainFormatNormalDiff.txt", "plain"),
                Arguments.of("file1.json", "file2.json", "jsonFormatNormalDiff.json", "json"),
                Arguments.of("empty.json", "file2.json", "emptyWithNormalDiff.txt", "stylish"),
                Arguments.of("empty.json", "file2.json", "plainFormatEmptyWithNormalDiff.txt", "plain"),
                Arguments.of("empty.json", "file2.json", "jsonFormatEmptyWithNormalDiff.json", "json"),
                Arguments.of("file1.json", "empty.json", "normalWithEmptyDiff.txt", "stylish"),
                Arguments.of("file1.json", "empty.json", "plainFormatNormalWithEmptyDiff.txt", "plain"),
                Arguments.of("empty.json", "empty.json", "emptyDiff.txt", "stylish"),
                Arguments.of("empty.json", "empty.json", "plainFormatEmptyDiff.txt", "plain"),

                Arguments.of("file1.yaml", "file2.yml", "normalDiff.txt", "stylish"),
                Arguments.of("file1.yaml", "file2.yml", "plainFormatNormalDiff.txt", "plain"),
                Arguments.of("file1.yaml", "file2.yml", "jsonFormatNormalDiff.json", "json")
        );
    }

    @ParameterizedTest(name = "Generate method test {index} -f {3} - for {0} and {1}, expected {2}")
    @MethodSource("filesForTest")
    void generateTest(String fileName1, String fileName2, String diffFileName, String format)
            throws Exception {
        var expected = readFixture(diffFileName);
        var path1 = getFixturePath(fileName1).toString();
        var path2 = getFixturePath(fileName2).toString();

        var actual = Differ.generate(path1, path2, format);

        assertEquals(expected, actual);
    }
}
