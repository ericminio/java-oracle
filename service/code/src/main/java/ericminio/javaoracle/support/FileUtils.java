package ericminio.javaoracle.support;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class FileUtils {

    public static String contentMinusTwoFirstLines(String filename) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(filename ), StandardCharsets.UTF_8);
        lines.remove(0);
        lines.remove(0);

        String content = "";
        for (int i=0; i<lines.size(); i++) {
            String line = lines.get(i);
            content += line;
            if (i != lines.size() - 1) {
                content += "\n";
            }
        }
        return content;
    }

    public static String exactContentOf(String filename) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(filename ), StandardCharsets.UTF_8);

        String content = "";
        for (int i=0; i<lines.size(); i++) {
            String line = lines.get(i);
            content += line;
            if (i != lines.size() - 1) {
                content += "\n";
            }
        }
        return content;
    }
}
