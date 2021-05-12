package ericminio.javaoracle.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExtractRecordTypeName {

    private List<Pattern> patterns;

    public ExtractRecordTypeName() {
        patterns = new ArrayList<>();
        patterns.add(Pattern.compile("as varray[\\(\\d*\\)]*[\\s]*of (.*)"));
        patterns.add(Pattern.compile("is varray[\\(\\d*\\)]*[\\s]*of (.*)"));
        patterns.add(Pattern.compile("as table of (.*)"));
        patterns.add(Pattern.compile("is table of (.*)"));
    }

    public String please(List<String> specification) {
        List<String> clean = new RemoveComments().please(specification);
        String statement = new JoinWith(" ").please(clean).trim().toLowerCase();

        return please(statement);
    }

    public String please(String statement) {
        for (int i=0; i < patterns.size(); i++) {
            Matcher matcher = patterns.get(i).matcher(statement);
            if (matcher.find()) {
                String candidate = matcher.group(1).trim();
                if (candidate.endsWith(";")) {
                    candidate = candidate.substring(0, candidate.length()-1);
                }
                return candidate;
            }
        }
        return null;
    }
}
