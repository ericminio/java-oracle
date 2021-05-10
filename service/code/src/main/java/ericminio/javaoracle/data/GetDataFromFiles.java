package ericminio.javaoracle.data;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class GetDataFromFiles {

    public Incoming please(String packageFile, String typesFile) throws IOException {
        List<String> packageSpecificationLines = Files.readAllLines(Paths.get(packageFile), StandardCharsets.UTF_8);
        List<String> lines = Files.readAllLines(Paths.get(typesFile), StandardCharsets.UTF_8);

        return new BuildIncoming().from(packageSpecificationLines, lines);
    }
}
