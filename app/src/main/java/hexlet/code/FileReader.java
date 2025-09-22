package hexlet.code;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

public class FileReader {
    private static Path getFilePath(String filePath) {
        return Paths.get(filePath)
                .toAbsolutePath().normalize();
    }

    private static String readFixture(String fileName) throws Exception {
        var path = getFilePath(fileName);
        return Files.readString(path).trim();
    }

    public static Map<String, String> getObjectMapper(String filePath) throws Exception{
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(FileReader.readFixture(filePath), new TypeReference<Map<String,String>>(){});
    }
}
