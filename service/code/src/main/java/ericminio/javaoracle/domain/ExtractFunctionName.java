package ericminio.javaoracle.domain;

import java.util.List;

public class ExtractFunctionName {

    public String please(List<String> specification) {
        for (int i=0; i < specification.size(); i++) {
            String line = specification.get(i).trim().toLowerCase();
            if (line.startsWith("function")) {
                int space = line.indexOf(" ");
                String tmp = line.substring(space).trim();
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