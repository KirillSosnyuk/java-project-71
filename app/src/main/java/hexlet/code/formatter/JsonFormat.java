package hexlet.code.formatter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import hexlet.code.model.ModeratedString;

import java.util.List;

public class JsonFormat {
    public static String createStyleJson(List<ModeratedString> changes) throws JsonProcessingException {
        var objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(changes);
    }

}
