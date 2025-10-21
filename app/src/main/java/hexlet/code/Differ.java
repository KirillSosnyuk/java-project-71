package hexlet.code;


import hexlet.code.formatter.Formattable;
import hexlet.code.formatter.Formatter;
import hexlet.code.model.ModeratedString;
import hexlet.code.model.Status;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.util.Objects;
import java.util.Comparator;

public class Differ {

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

        Set<String> generalKeys = new HashSet<>(firstFileContent.keySet());
        generalKeys.addAll(secondFileContent.keySet());


        List<ModeratedString> changes = new ArrayList<>();

        for (String key: generalKeys) {
            changes.add(new ModeratedString(key,
                    firstFileContent.getOrDefault(key, null),
                    secondFileContent.getOrDefault(key, null),
                    moderateState(firstFileContent, secondFileContent, key)));
        }

        changes.sort(Comparator.comparing(ModeratedString::key));

        Formattable formatter = Formatter.create(format);
        return formatter.format(changes);
    }

}
