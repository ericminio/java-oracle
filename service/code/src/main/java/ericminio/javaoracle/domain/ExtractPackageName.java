package ericminio.javaoracle.domain;

import java.util.List;

public class ExtractPackageName {

    public String please(List<String> specification) {
        for (int i=0; i < specification.size(); i++) {
            String line = specification.get(i).trim().toLowerCase();
            if (line.startsWith("package")) {
                int space = line.indexOf(" ");
                String after = line.substring(space + 1).trim();
                if (after.indexOf(" ") != -1) {
                    after = after.substring(0, after.indexOf(" ")).trim();
                }
                return after.toLowerCase();
            }
        }
        return null;
    }
}