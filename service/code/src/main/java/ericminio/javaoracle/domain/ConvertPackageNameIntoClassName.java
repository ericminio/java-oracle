package ericminio.javaoracle.domain;

public class ConvertPackageNameIntoClassName {

    public String please(String input) {
        String name = "";
        String[] parts = input.split("_");
        for (int i=0; i<parts.length; i++) {
            name += new Capitalize().please(parts[i]);
        }
        return name;
    }
}