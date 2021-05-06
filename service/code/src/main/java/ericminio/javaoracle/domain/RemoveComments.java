package ericminio.javaoracle.domain;

import java.util.ArrayList;
import java.util.List;

public class RemoveComments {

    public List<String> please(List<String> lines) {
        List<String> clean = new ArrayList<>();
        for (int i=0; i<lines.size(); i++) {
            String line = lines.get(i);
            if (line.indexOf("--") != -1) {
                line = line.substring(0, line.indexOf("--"));
            }
            if (line.indexOf("/**") != -1) {
                line = line.substring(0, line.indexOf("/**"));
            }
            clean.add(line);
        }
        return clean;
    }
}
