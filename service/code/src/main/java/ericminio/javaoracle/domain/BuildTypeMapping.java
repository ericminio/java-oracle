package ericminio.javaoracle.domain;

import ericminio.javaoracle.support.PascalCase;

import java.util.List;

public class BuildTypeMapping {

    public String please(List<String> types) {
        String mapping = "";
        for (int i=0; i<types.size(); i++) {
            String type = types.get(i);
            if (TypeMapperFactory.isCutomType(type)) {
                mapping += "        connection.getTypeMap().put(" + new PascalCase().please(type) + ".NAME, " + new PascalCase().please(type) + ".class);\n";
            }
        }
        return mapping;
    }
}
