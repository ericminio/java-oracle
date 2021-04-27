package ericminio.javaoracle;

import java.util.List;

public class ExtractFunctionName {

    public String please(List<String> specification) {
        for (int i=0; i < specification.size(); i++) {
            String line = specification.get(i).trim();
            if (line.startsWith("FUNCTION")) {
                String tmp = line.replace("FUNCTION", "").trim();
                int firstSpace = tmp.indexOf(" ");
                if (firstSpace == -1) { firstSpace = tmp.length(); }
                int firstParenthesis = tmp.indexOf("(");
                if (firstParenthesis == -1) { firstParenthesis = tmp.length(); }
                int stop = Math.min(firstParenthesis, firstSpace);
                return tmp.substring(0, stop);
            }
        }
        return null;
    }
}