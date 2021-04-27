package ericminio.javaoracle;

import java.util.List;

public class ExtractFunctionParameters {

    public Parameters please(List<String> functionSpecification) {
        Parameters parameters = new Parameters();

        String function = "";
        for (int i=0; i<functionSpecification.size(); i++) {
            function += functionSpecification.get(i);
        }
        if (function.indexOf("(") != -1) {
            function = function.substring(function.indexOf("(") + 1);
            function = function.substring(0, function.indexOf(")"));
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
