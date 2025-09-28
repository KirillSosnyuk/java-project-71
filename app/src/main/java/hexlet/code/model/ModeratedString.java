package hexlet.code.model;

public record ModeratedString(String key, Object previousValue, Object currentValue, String status) {
}
