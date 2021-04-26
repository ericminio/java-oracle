package ericminio.javaoracle;

import java.util.ArrayList;
import java.util.List;

public class ExtractFunctionSpecifications {

    public List<List<String>> please(List<String> packageSpecification) {
        List<List<String>> functionSpecifications = new ArrayList<>();
        List<String> functionSpecification = new ArrayList<>();
        for (int i=0; i<packageSpecification.size(); i++) {
            String line = packageSpecification.get(i).trim();
            if (line.toUpperCase().startsWith("FUNCTION") || functionSpecification.size() > 0) {
                functionSpecification.add(line);
                if (line.endsWith(";")) {
                    functionSpecifications.add(functionSpecification);
                    functionSpecification = new ArrayList<>();
                }
            }
        }
        return functionSpecifications;
    }
}
