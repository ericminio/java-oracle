package ericminio.javaoracle;

public class Capitalize {

    public String please(String input) {
        return input.substring(0, 1).toUpperCase() + input.substring(1).toLowerCase();
    }
}