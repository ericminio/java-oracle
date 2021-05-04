package ericminio.javaoracle.domain;

import ericminio.javaoracle.support.PascalCase;

import java.util.List;

public class BuildTypeMapping {

    private TypeMapperFactory typeMapperFactory;
    private PascalCase pascalCase;

    public BuildTypeMapping(TypeMapperFactory typeMapperFactory) {
        this.typeMapperFactory = typeMapperFactory;
        this.pascalCase = new PascalCase();
    }

    public String please(List<String> types) {
        String mapping = "";
        for (int i=0; i<types.size(); i++) {
            String type = types.get(i);
            if (typeMapperFactory.isCutomType(type)) {
                String className = pascalCase.please(type);
                if (typeMapperFactory.isArrayType(type)) {
                    pascalCase = new PascalCase();
                    className = pascalCase.please(typeMapperFactory.recordTypeOfArrayType(type));
                }
                mapping += "        connection.getTypeMap().put(" + className + ".NAME, " + className + ".class);\n";
            }
        }
        return mapping;
    }
}
