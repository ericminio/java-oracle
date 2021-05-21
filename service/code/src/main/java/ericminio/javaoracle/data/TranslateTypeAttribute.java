package ericminio.javaoracle.data;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TranslateTypeAttribute {

    private static String token = "%TYPE";

    public List<String> please(List<String> packageSpecification) throws SQLException {
        List<String> translatedLines = new ArrayList<>();
        for (int i=0; i<packageSpecification.size(); i++) {
            String line = packageSpecification.get(i).trim();
            while (line.indexOf(token) >=0) {
                line = translateFirst(line);
            }
            translatedLines.add(line);
        }
        return translatedLines;
    }

    private String translateFirst(String line) throws SQLException {
        String before = line.substring(0, line.indexOf(token));
        String source = before.substring(before.lastIndexOf(" ")+1);
        String translation = translateSource(source);

        return line.replace(source+token, translation);
    }

    private String translateSource(String source) throws SQLException {
        String[] split = source.split("\\.");
        String table = split[0];
        String column = split[1];
        return new Database().selectColumnType(table, column);
    }
}
