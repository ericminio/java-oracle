package ericminio.javaoracle.domain;

import java.util.List;

public class ExtractFunctionName {

    private static String token = "function";

    public String please(List<String> specification) {
        List<String> clean = new RemoveComments().please(specification);
        String line = new JoinWith(" ").please(clean).trim().toLowerCase();
        if (line.indexOf(token) != -1) {
            line = line.substring(line.indexOf(token) + token.length()).trim();
            int firstSpace = line.indexOf(" ");
            if (firstSpace == -1) {
                firstSpace = line.length();
            }
            int firstParenthesis = line.indexOf("(");
            if (firstParenthesis == -1) {
                firstParenthesis = line.length();
            }
            int stop = Math.min(firstParenthesis, firstSpace);
            return line.substring(0, stop).trim();
        }
        return null;
    }
}