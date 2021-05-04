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
                return matcher.group(1);
            }
        }
        return null;
    }
}
