package ericminio.javaoracle.domain;

import java.util.ArrayList;
import java.util.List;

public class GenerateImports {

    public String please(String code) {
        String[] lines = code.split("\n");
        List<String> imports = new ArrayList<>();
        List<String> other = new ArrayList<>();
        for (int i=0; i<lines.length; i++) {
            if (lines[i].trim(). startsWith("import ")) {
                imports.add(lines[i].trim());
            }
            else {
                other.add(lines[i]);
            }
        }

        if (code.indexOf("BigDecimal ") != -1) {
            imports.add("import java.math.BigDecimal;");
        }
        if (code.indexOf("Date ") != -1) {
            imports.add("import java.util.Date;");
        }
        if (code.indexOf(" Types.") != -1) {
            imports.add("import java.sql.Types;");
        }
        if (code.indexOf("Clob ") != -1) {
            imports.add("import java.sql.Clob;");
        }
        if (code.indexOf("ResultSet ") != -1) {
            imports.add("import java.sql.ResultSet;");
        }

        sort(imports);

        String withNeedeImports = new JoinWith("\n").please(imports) + "\n";
        for (int i=0 ; i<other.size(); i++) {
            withNeedeImports+= other.get(i) + "\n";
        }

        return withNeedeImports;
    }

    public void sort(List<String> collection) {
        for (int i=0; i<collection.size(); i++) {
            for (int j=i+1; j<collection.size(); j++) {
                if (collection.get(j).compareTo(collection.get(i)) < 0) {
                    String tmp = collection.get(j);
                    collection.set(j, collection.get(i));
                    collection.set(i, tmp);
                }
            }
        }
    }
}
