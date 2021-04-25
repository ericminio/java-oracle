package ericminio.javaoracle;

import java.util.List;

public class ExtractFunctionName {

    public String please(List<String> specification) {
        String match = specification
            .stream()
            .filter(line -> line.trim().startsWith("FUNCTION"))
            .findFirst()
            .orElse(null);
        String name = match.trim().split(" ")[1];

        return name;
    }
}