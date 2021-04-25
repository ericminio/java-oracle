package ericminio.javaoracle;

import java.util.List;

public class ExtractPackageName {

    public String please(List<String> specification) {
        String match = specification
            .stream()
            .filter(line -> line.trim().startsWith("PACKAGE"))
            .findFirst()
            .orElse(null);
        String name = match.replace("PACKAGE", "").trim().toLowerCase();

        return name;
    }
}