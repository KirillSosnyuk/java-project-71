package hexlet.code;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.yaml.snakeyaml.Yaml;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

public class FileReader {
    private static Path getFilePath(String filePath) {
        return Paths.get(filePath).toAbsolutePath().normalize();
    }

    static String readFixture(String fileName) throws Exception {
        var path = getFilePath(fileName);
        return Files.readString(path).trim();
    }

    public static Map<String, Object> getData(String filePath) throws Exception {
        String fileFormat = filePath.substring(filePath.lastIndexOf("."));
        return switch (fileFormat) {
            case ".json" -> new ObjectMapper().readValue(FileReader.readFixture(filePath),
                    new TypeReference<Map<String, Object>>() { });
            case ".yaml", ".yml" -> new Yaml().load(FileReader.readFixture(filePath));
            default -> throw new IllegalStateException("Unexpected value: " + fileFormat);
        };
    }

}
