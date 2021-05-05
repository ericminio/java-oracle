package ericminio.javaoracle.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExtractRecordTypeName {

    private List<Pattern> patterns;

    public ExtractRecordTypeName() {
        patterns = new ArrayList<>();
        patterns.add(Pattern.compile("as varray[\\(\\d*\\)]* of (.*)"));
        patterns.add(Pattern.compile("is varray[\\(\\d*\\)]* of (.*)"));
        patterns.add(Pattern.compile("as table of (.*)"));
        patterns.add(Pattern.compile("is table of (.*)"));
    }

    public String please(List<String> specification) {
        String statement = "";
        for (int i=0; i < specification.size(); i++) {
            statement += specification.get(i).trim().toLowerCase();
            statement += " ";
        }
        statement = statement.trim();
        for (int i=0; i < patterns.size(); i++) {
            Matcher matcher = patterns.get(i).matcher(statement);
            if (matcher.find()) {
                String candidate = matcher.group(1).trim();
                if (candidate.indexOf("--") != -1) {
                    candidate = candidate.substring(0, candidate.indexOf("--")).trim();
                }
                if (candidate.endsWith(";")) {
                    candidate = candidate.substring(0, candidate.length()-1);
                }
                return candidate;
            }
        }
        return null;
    }
}
