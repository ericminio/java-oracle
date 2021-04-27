package ericminio.javaoracle.support;

public class PascalCase {

    public String please(String input) {
        return new Capitalize().please(new CamelCase().please(input));
    }
}