package ericminio.javaoracle.domain;

import ericminio.javaoracle.support.PascalCase;

import java.util.ArrayList;
import java.util.List;

public class BuildTypeMapping {

    private TypeMapperFactory typeMapperFactory;
    private PascalCase pascalCase;

    public BuildTypeMapping(TypeMapperFactory typeMapperFactory) {
        this.typeMapperFactory = typeMapperFactory;
        this.pascalCase = new PascalCase();
    }

    public String please(List<String> types) {
        List<String> customTypes = new ArrayList<>();
        for (int i=0; i<types.size(); i++) {
            collect(customTypes, types.get(i));
        }
        String mapping = "";
        for (int i=0; i<customTypes.size(); i++) {
            mapping += mapping(customTypes.get(i));
        }
        return mapping;
    }

    private void collect(List<String> customTypes, String type) {
        if (!typeMapperFactory.isCustomType(type)) {
            return;
        }
        if (typeMapperFactory.isArrayType(type)) {
            String recordType = typeMapperFactory.recordTypeOfArrayType(type);
            collect(customTypes, recordType);
            return;
        }
        if (!customTypes.contains(type)) {
            customTypes.add(type);
        }
        List<String> fieldtypes = typeMapperFactory.customTypesOfFields(type);
        for (int i=0; i<fieldtypes.size(); i++) {
            collect(customTypes, fieldtypes.get(i));
        }
    }

    private String mapping(String type) {
        return "        connection.getTypeMap().put(" + pascalCase.please(type) + ".NAME, " + pascalCase.please(type) + ".class);\n";
    }
}
