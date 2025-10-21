package hexlet.code.formatter;



public class Formatter {
    public static Formattable create(String format) {
        return switch (format) {
            case "stylish" -> new StylishFormat();
            case "json" -> new JsonFormat();
            case "plain" -> new PlainFormat();
            default -> throw new IllegalStateException("Unexpected value: "
                    + format);

        };
    }

}
