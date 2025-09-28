package hexlet.code;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import hexlet.code.model.ModeratedString;

import java.util.Map;
import java.util.LinkedHashMap;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

public class Differ {

    private static String createStyleJson(Map<Integer, ModeratedString> changes) throws JsonProcessingException {
        var objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(changes.values());
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

    private static String createStylePlain(Map<Integer, ModeratedString> changes) {
        StringBuilder result = new StringBuilder();
        var currentChanges = changes.values();
        for (ModeratedString currentString: currentChanges) {
            var status = currentString.status();
            if (status.equals("unchanged")) {
                continue;
            }

            var completedString = switch (status) {
                case "added" -> plainString(currentString.key(), status)
                        + " with value: "
                        + parseValuePlain(currentString.currentValue());
                case "removed" -> plainString(currentString.key(), status);
                case "updated" -> plainString(currentString.key(), status)
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

    private static String createStyleStylish(Map<Integer, ModeratedString> changes) {
        StringBuilder result = new StringBuilder("{\n");
        var currentChanges = changes.values();
        for (ModeratedString currentString: currentChanges) {
            var status = currentString.status();
            var completedString = switch (status) {
                case "added" -> stylishString(currentString.key(), currentString.currentValue(), "+");
                case "removed" -> stylishString(currentString.key(), currentString.previousValue(), "-");
                case "updated" -> stylishString(currentString.key(), currentString.previousValue(), "-")
                        + stylishString(currentString.key(), currentString.currentValue(), "+");
                case "unchanged" -> stylishString(currentString.key(), currentString.currentValue(), " ");
                default -> throw new IllegalStateException("Unexpected value: " + status);
            };
            result.append(completedString);
        }
        result.append("}");
        return result.toString();

    }
    private static String createStyle(Map<Integer, ModeratedString> changes,
                                      String format) throws JsonProcessingException {
        return switch (format) {
            case "stylish" -> createStyleStylish(changes);
            case "json" -> createStyleJson(changes);
            case "plain" -> createStylePlain(changes);
            default -> throw new IllegalStateException("Unexpected value: "
                    + format);

        };
    }

    private static String moderateState(Map<String, Object> content1,
                                          Map<String, Object> content2, String currentKey) {

        return !content1.containsKey(currentKey) ? "added"
                : !content2.containsKey(currentKey) ? "removed"
                : !Objects.equals(content1.get(currentKey), content2.get(currentKey)) ? "updated"
                : "unchanged";


    }
    public static String generate(String firstFilePath, String secondFilePath) throws Exception {
        return generate(firstFilePath, secondFilePath, "stylish");



    }
    public static String generate(String firstFilePath, String secondFilePath, String format) throws Exception {
        Map<String, Object> firstFileContent = FileReader.getData(String.valueOf(firstFilePath));
        Map<String, Object> secondFileContent = FileReader.getData(String.valueOf(secondFilePath));

        Set<String> generalKeys = new TreeSet<>();
        generalKeys.addAll(firstFileContent.keySet());
        generalKeys.addAll(secondFileContent.keySet());

        Map<Integer, ModeratedString> changes = new LinkedHashMap<>();
        int counter = 0;
        for (String key: generalKeys) {
            changes.put(counter++, new ModeratedString(key,
                    firstFileContent.getOrDefault(key, null),
                    secondFileContent.getOrDefault(key, null),
                    moderateState(firstFileContent, secondFileContent, key)));
        }
        return createStyle(changes, format);



    }
}
