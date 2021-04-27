package ericminio.javaoracle;

public class PlaceholderList {

    public String please(int count) {
        String placeholders = "";

        for (int i=0; i<count; i++) {
            placeholders += "?";
            if (i != count-1) {
                placeholders += ", ";
            }
        }
        return placeholders;
    }
}
