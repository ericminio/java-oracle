package ericminio.javaoracle;

import java.util.List;

public class ExtractFunctionName {

    public String please(List<String> specification) {
        for (int i=0; i < specification.size(); i++) {
            String line = specification.get(i).trim();
            if (line.startsWith("FUNCTION")) {
                return line.split(" ")[1];
            }
        }
        return null;
    }
}