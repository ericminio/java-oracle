package ericminio.javaoracle;

import java.util.List;

public class ExtractPackageName {

    public String please(List<String> specification) {
        for (int i=0; i < specification.size(); i++) {
            String line = specification.get(i).trim();
            if (line.startsWith("PACKAGE")) {
                return line.replace("PACKAGE", "").trim().toLowerCase();
            }
        }
        return null;
    }
}