package ericminio.javaoracle.domain;

import java.util.List;

public class ExtractParameters {

    public Parameters please(List<String> specification) {
        Parameters parameters = new Parameters();

        String function = "";
        for (int i=0; i<specification.size(); i++) {
            function += specification.get(i);
        }
        if (function.indexOf("(") != -1) {
            function = function.substring(function.indexOf("(") + 1);
            function = function.substring(0, function.lastIndexOf(")"));
            String[] parts = function.split(",");

            for (int i = 0; i < parts.length; i++) {
                if (parts[i].trim().length() > 0) {
                    parameters.add(parts[i].trim());
                }
            }
        }

        return parameters;
    }
}
