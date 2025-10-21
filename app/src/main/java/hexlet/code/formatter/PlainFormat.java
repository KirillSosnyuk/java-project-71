package hexlet.code.formatter;

import hexlet.code.model.ModeratedString;
import hexlet.code.model.Status;

import java.util.List;

final class PlainFormat implements Formattable {
    public static String parseValuePlain(Object value) {
        if (value == null) {
            return "null";
        }
        if (value instanceof Boolean b) {
            return b.toString();
        }
        if (value instanceof Number n) {
            return n.toString();
        }
        if (value instanceof String s) {
            return String.format("'%s'", s);
        }
        return "[complex value]";
    }

    public String format(List<ModeratedString> changes) {
        StringBuilder result = new StringBuilder();
        for (ModeratedString currentString: changes) {
            var status = currentString.status();
            if (status.equals(Status.UNTOUCHED)) {
                continue;
            }

            var completedString = switch (status) {
                case Status.ADDED -> plainString(currentString.key(), "added")
                        + " with value: "
                        + parseValuePlain(currentString.currentValue());
                case Status.REMOVED -> plainString(currentString.key(), "removed");
                case Status.UPDATED -> plainString(currentString.key(), "updated")
                        + ". From "
                        + parseValuePlain(currentString.previousValue())
                        + " to "
                        + parseValuePlain(currentString.currentValue());
                default -> throw new IllegalStateException("Unexpected value: " + status);
            } + "\n";

            result.append(completedString);
        }
        return result.toString().trim();
    }

    public static String plainString(String key, String status) {
        return "Property '"
                + key
                + "' "
                + "was "
                + status;
    }
}
