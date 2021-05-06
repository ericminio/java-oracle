package ericminio.javaoracle.domain;

import java.util.List;

public class ExtractPackageName {

    private static String token = "package";

    public String please(List<String> specification) {
        List<String> clean = new RemoveComments().please(specification);
        String line = new JoinWith(" ").please(clean).trim().toLowerCase();
        if (line.indexOf(token) != -1) {
            line = line.substring(line.indexOf(token) + token.length());
            if (line.indexOf(" ") != -1) {
                line = line.substring(line.indexOf(" ") + 1).trim();
                if (line.indexOf(" ") != -1) {
                    line = line.substring(0, line.indexOf(" ")).trim();
                }
                return line;
            }
        }
        return null;
    }
}