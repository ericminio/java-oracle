package ericminio.javaoracle.support;

public class CamelCase {

    public String please(String input) {
        String output = "";
        String[] parts = input.split("_");
        for (int i=0; i< parts.length; i++) {
            String part = new Capitalize().please(parts[i].toLowerCase());
            if (i == 0) {
                part = part.toLowerCase();
            }
            output += part;
        }

        return output;
    }
}