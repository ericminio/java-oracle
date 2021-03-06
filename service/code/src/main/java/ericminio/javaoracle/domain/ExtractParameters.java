package ericminio.javaoracle.domain;

import java.util.List;

public class ExtractParameters {

    public Parameters please(List<String> specification) {
        Parameters parameters = new Parameters();
        List<String> clean = new RemoveComments().please(specification);
        String function = new JoinWith("").please(clean);
        if (function.toLowerCase().indexOf("varray") != -1) {
            return parameters;
        }
        if (function.indexOf("(") != -1) {
            String list = function.substring(function.indexOf("(") + 1);
            list = list.substring(0, list.lastIndexOf(")"));

            String candidate = "";
            boolean inDetails = false;
            int current = 0;
            while (current < list.length()) {
                String currentChar = list.substring(current, current+1);
                if ("(".equalsIgnoreCase(currentChar)) {
                    inDetails = true;
                }
                if (")".equalsIgnoreCase(currentChar)) {
                    inDetails = false;
                }
                if (",".equalsIgnoreCase(currentChar) && !inDetails) {
                    if (candidate.trim().length() > 0) {
                        parameters.add(candidate.trim());
                    }
                    candidate = "";
                }
                else {
                    candidate += currentChar;
                }
                current ++;
            }
            if (candidate.trim().length() > 0) {
                parameters.add(candidate.trim());
            }
        }

        return parameters;
    }
}
