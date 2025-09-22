package hexlet.code;


import java.util.*;

public class Differ{
    private static String moderateString(String currentKey, Object currentValue, String sign) {
        return "  " + sign + " " + currentKey + ": " + currentValue.toString() + "\n";
    }
    private static String moderateChanges(Map<String, Object> content1, Map<String, Object> content2, String currentKey) {

        String state = !content1.containsKey(currentKey) ? "ADDED"
                : !content2.containsKey(currentKey) ? "DELETED"
                : !Objects.equals(content1.get(currentKey), content2.get(currentKey)) ? "CHANGED"
                : "UNCHANGED";

        return switch (state) {
            case "ADDED" -> moderateString(currentKey, content2.get(currentKey), "+");
            case "DELETED" -> moderateString(currentKey, content1.get(currentKey), "-");
            case "CHANGED" -> moderateString(currentKey, content1.get(currentKey), "-") +
                    moderateString(currentKey, content2.get(currentKey), "+");
            case "UNCHANGED" -> moderateString(currentKey, content1.get(currentKey), " ");
            default -> throw new IllegalStateException("Unexpected value: " + state);
        };

    }

    public static String generate(Map<String, Object> fileContent1, Map<String, Object> fileContent2) {
        Set<String> generalKeys = new TreeSet<>();
        generalKeys.addAll(fileContent1.keySet());
        generalKeys.addAll(fileContent2.keySet());

        StringBuilder result = new StringBuilder("{\n");

        for(String key: generalKeys) {
            result.append(Differ.moderateChanges(fileContent1, fileContent2, key));
        }

        result.append("}");
        return result.toString();



    }
}
