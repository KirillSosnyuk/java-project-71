package hexlet.code;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import hexlet.code.model.ModeratedString;
import hexlet.code.model.Status;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;
import java.util.HashSet;
import java.util.Objects;

public class Differ {

    private static String createStyleJson(List<ModeratedString> changes) throws JsonProcessingException {
        var objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(changes);
    }

    private static String parseValuePlain(Object value) {
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

    private static String createStylePlain(List<ModeratedString> changes) {
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

    private static String plainString(String key, String status) {
        return "Property '"
                + key
                + "' "
                + "was "
                + status;
    }

    private static String stylishString(String key, Object value, String sign) {
        return "  "
                + sign
                + " "
                + key
                + ": "
                + Objects.toString(value, "null")
                + "\n";
    }

    private static String createStyleStylish(List<ModeratedString> changes) {
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
    private static String createStyle(List<ModeratedString> changes,
                                      String format) throws JsonProcessingException {
        return switch (format) {
            case "stylish" -> createStyleStylish(changes);
            case "json" -> createStyleJson(changes);
            case "plain" -> createStylePlain(changes);
            default -> throw new IllegalStateException("Unexpected value: "
                    + format);

        };
    }

    private static Status moderateState(Map<String, Object> content1,
                                          Map<String, Object> content2, String currentKey) {

        return !content1.containsKey(currentKey) ? Status.ADDED
                : !content2.containsKey(currentKey) ? Status.REMOVED
                : !Objects.equals(content1.get(currentKey), content2.get(currentKey)) ? Status.UPDATED
                : Status.UNTOUCHED;


    }
    public static String generate(String firstFilePath, String secondFilePath) throws Exception {
        return generate(firstFilePath, secondFilePath, "stylish");



    }
    public static String generate(String firstFilePath, String secondFilePath, String format) throws Exception {
        Map<String, Object> firstFileContent = FileReader.getData(String.valueOf(firstFilePath));
        Map<String, Object> secondFileContent = FileReader.getData(String.valueOf(secondFilePath));

        Set<String> generalKeys = new HashSet<>();
        generalKeys.addAll(firstFileContent.keySet());
        generalKeys.addAll(secondFileContent.keySet());

        Set<String> sortedKeys = new TreeSet<>(generalKeys);

        List<ModeratedString> changes = new ArrayList<>();

        for (String key: sortedKeys) {
            changes.add(new ModeratedString(key,
                    firstFileContent.getOrDefault(key, null),
                    secondFileContent.getOrDefault(key, null),
                    moderateState(firstFileContent, secondFileContent, key)));
        }
        return createStyle(changes, format);



    }
}
