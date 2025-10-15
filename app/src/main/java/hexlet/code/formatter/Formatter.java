package hexlet.code.formatter;

import com.fasterxml.jackson.core.JsonProcessingException;
import hexlet.code.model.ModeratedString;

import java.util.List;


public class Formatter {
    public static String createStyle(List<ModeratedString> changes,
                                      String format) throws JsonProcessingException {
        return switch (format) {
            case "stylish" -> StylishFormat.createStyleStylish(changes);
            case "json" -> JsonFormat.createStyleJson(changes);
            case "plain" -> PlainFormat.createStylePlain(changes);
            default -> throw new IllegalStateException("Unexpected value: "
                    + format);

        };
    }

}
