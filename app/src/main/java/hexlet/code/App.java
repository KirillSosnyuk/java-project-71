package hexlet.code;


import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;


import java.nio.file.Path;
import java.util.concurrent.Callable;


@Command(
        name = "gendiff",
        mixinStandardHelpOptions = true,
        version = "gendiff 1.0",
        description = "Compares two configuration files and shows a difference."
)
public class App implements Callable {
    @Parameters(
            index = "0",
            description = "path to first file",
            paramLabel = "filepath1"
    )
    private Path filePath1;

    @Parameters(
            index = "1",
            description = "path to second file",
            paramLabel = "filepath2"
    )
    private Path filePath2;

    @Option(
            names = {"-f", "--format"},
            description = "output format [default: stylish]",
            defaultValue = "stylish",
            paramLabel = "format"
    )
    private String formatType;


    @Override
    public String call() {
        try {
            var test = Differ.generate(String.valueOf(filePath1),
                    String.valueOf(filePath2),
                   String.valueOf(formatType));
            System.out.println(test);
            return test;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        int exitCode = new CommandLine(new App()).execute(args);
        System.exit(exitCode);
    }
}
