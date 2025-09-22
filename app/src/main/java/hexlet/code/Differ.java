package hexlet.code;


import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

public class Differ {
    private static String moderateString(String currentKey, Object currentValue, String sign) {
        return "  " + sign + " " + currentKey + ": " + currentValue.toString() + "\n";
    }
    private static String moderateChanges(Map<String, Object> content1,
                                          Map<String, Object> content2, String currentKey) {

        String state = !content1.containsKey(currentKey) ? "ADDED"
                : !content2.containsKey(currentKey) ? "DELETED"
                : !Objects.equals(content1.get(currentKey), content2.get(currentKey)) ? "CHANGED"
                : "UNCHANGED";

        return switch (state) {
            case "ADDED" -> moderateString(currentKey, content2.get(currentKey), "+");
            case "DELETED" -> moderateString(currentKey, content1.get(currentKey), "-");
            case "CHANGED" -> moderateString(currentKey, content1.get(currentKey), "-")
                    + moderateString(currentKey, content2.get(currentKey), "+");
            case "UNCHANGED" -> moderateString(currentKey, content1.get(currentKey), " ");
            default -> throw new IllegalStateException("Unexpected value: "
                    + state);
        };

    }

    public static String generate(String firstFilePath, String secondFilePath) throws Exception {
        Map<String, Object> firstFileContent = FileReader.getData(String.valueOf(firstFilePath));
        Map<String, Object> secondFileContent = FileReader.getData(String.valueOf(secondFilePath));

        Set<String> generalKeys = new TreeSet<>();
        generalKeys.addAll(firstFileContent.keySet());
        generalKeys.addAll(secondFileContent.keySet());

        StringBuilder result = new StringBuilder("{\n");

        for (String key: generalKeys) {
            result.append(Differ.moderateChanges(firstFileContent, secondFileContent, key));
        }

        result.append("}");
        return result.toString();



    }
}
