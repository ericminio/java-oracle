package ericminio.javaoracle.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExtractTypeName {

    private List<Pattern> patterns;

    public ExtractTypeName() {
        patterns = new ArrayList<>();
        patterns.add(Pattern.compile("type (.*) as"));
        patterns.add(Pattern.compile("type (.*) is"));
    }

    public String please(List<String> specification) {
        String statement = new JoinWith(" ").please(specification).trim().toLowerCase();
        for (int i=0; i < patterns.size(); i++) {
            Matcher matcher = patterns.get(i).matcher(statement);
            if (matcher.find()) {
                String candidate = matcher.group(1).trim();
                if (candidate.startsWith("\"")) {
                    candidate = candidate.substring(1);
                }
                if (candidate.endsWith("\"")) {
                    candidate = candidate.substring(0, candidate.length()-1);
                }

                return candidate.trim();
            }
        }
        throw new RuntimeException("type name not found in\n" + statement);
    }
}
