package hexlet.code.formatter;

import hexlet.code.model.ModeratedString;
import hexlet.code.model.Status;

import java.util.List;
import java.util.Objects;

public class StylishFormat implements Formattable {
    public static String stylishString(String key, Object value, String sign) {
        return "  "
                + sign
                + " "
                + key
                + ": "
                + Objects.toString(value, "null")
                + "\n";
    }

    public String format(List<ModeratedString> changes) {
        StringBuilder result = new StringBuilder("{\n");
        for (ModeratedString currentString: changes) {
            var status = currentString.status();
            var completedString = switch (status) {
                case Status.ADDED -> stylishString(currentString.key(), currentString.currentValue(), "+");
                case Status.REMOVED -> stylishString(currentString.key(), currentString.previousValue(), "-");
                case Status.UPDATED -> stylishString(currentString.key(), currentString.previousValue(), "-")
                        + stylishString(currentString.key(), currentString.currentValue(), "+");
                case Status.UNTOUCHED -> stylishString(currentString.key(), currentString.currentValue(), " ");
            };
            result.append(completedString);
        }
        result.append("}");
        return result.toString();
    }
}
