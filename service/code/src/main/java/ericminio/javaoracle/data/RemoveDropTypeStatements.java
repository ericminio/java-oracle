package ericminio.javaoracle.data;

import java.util.ArrayList;
import java.util.List;

public class RemoveDropTypeStatements {

    public List<String> from(List<String> lines) {
        List<String> clean = new ArrayList<>();
        for (int i=0; i<lines.size(); i++) {
            String candidate = lines.get(i).trim().toLowerCase();
            if (! candidate.startsWith("drop type")) {
                clean.add(candidate);
            }
        }
        return clean;
    }
}
