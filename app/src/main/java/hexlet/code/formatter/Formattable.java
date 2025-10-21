package hexlet.code.formatter;

import com.fasterxml.jackson.core.JsonProcessingException;
import hexlet.code.model.ModeratedString;

import java.util.List;

public interface Formattable {
    String format(List<ModeratedString> changes) throws JsonProcessingException;
}
