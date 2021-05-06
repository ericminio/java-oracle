package ericminio.javaoracle.domain;

import java.util.List;

public class JoinWith {

    private String delimiter;

    public JoinWith(String delimiter) {
        this.delimiter = delimiter;
    }

    public String please(List<String> lines) {
        String oneLine = "";
        for (int i=0; i<lines.size(); i++) {
            oneLine += lines.get(i);
            if (i < lines.size()-1) {
                oneLine += delimiter;
            }
        }
        return oneLine;
    }
}
