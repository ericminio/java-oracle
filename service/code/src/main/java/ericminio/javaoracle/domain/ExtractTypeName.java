package ericminio.javaoracle.domain;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExtractTypeName {

    private Pattern pattern;

    public ExtractTypeName() {
        pattern = Pattern.compile("type (.*) as object");
    }

    public String please(List<String> specification) {
        for (int i=0; i < specification.size(); i++) {
            String line = specification.get(i).trim().toLowerCase();
            Matcher matcher = pattern.matcher(line);
            if (matcher.find()) {
                return matcher.group(1);
            }
        }
        return null;
    }
}
