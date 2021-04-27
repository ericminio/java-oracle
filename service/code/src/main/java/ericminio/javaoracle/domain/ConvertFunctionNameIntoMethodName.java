package ericminio.javaoracle.domain;

public class ConvertFunctionNameIntoMethodName {

    public String please(String input) {
        String methodName = "";
        String[] parts = input.split("_");
        for (int i=0; i< parts.length; i++) {
            String part = parts[i].toLowerCase();
            if (i != 0) {
                part = new Capitalize().please(part);
            }
            methodName += part;
        }

        return methodName;
    }
}