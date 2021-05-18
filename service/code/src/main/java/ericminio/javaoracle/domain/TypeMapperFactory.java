package ericminio.javaoracle.domain;

import java.util.ArrayList;
import java.util.List;

public class TypeMapperFactory {

    private List<List<String>> typeSpecifications;

    public TypeMapperFactory() {
        this(new ArrayList<List<String>>());
    }

    public TypeMapperFactory(List<List<String>> typeSpecifications) {
        this.typeSpecifications = typeSpecifications;
    }

    public TypeMapper of(String incoming) {
        String normalized = incoming.toUpperCase();
        if (normalized.startsWith("NUMBER")) {
            return new TypeMapperNumber();
        }
        if (normalized.startsWith("VARCHAR")) {
            return new TypeMapperString();
        }
        if (normalized.startsWith("DATE")) {
            return new TypeMapperDate();
        }
        if (normalized.startsWith("CLOB")) {
            return new TypeMapperClobType();
        }
        if (isCustomType(incoming)) {
            if (isArrayType(incoming)) {
                return new TypeMapperArrayType(incoming);
            }
            if (isCursorType(incoming)) {
                return new TypeMapperCursorType(incoming);
            }
            return new TypeMapperCustomType(incoming);
        }

        throw new RuntimeException(("Teach me about type \"" + incoming + "\""));
    }

    public boolean isCustomType(String type) {
        return type.indexOf("_") != -1;
    }

    public boolean isCursorType(String type) {
        if (! isCustomType(type)) {
            return false;
        }
        List<String> specification = specificationOfType(type);
        String statement = new JoinWith(" ").please(specification).trim().toLowerCase();

        return statement.indexOf(" ref cursor") != -1;
    }

    public boolean isArrayType(String type) {
        if (! isCustomType(type)) {
            return false;
        }
        List<String> specification = specificationOfType(type);
        String statement = new JoinWith(" ").please(specification).trim().toLowerCase();

        return statement.indexOf(" table") != -1 || statement.indexOf(" varray") != -1;
    }

    public String recordTypeOfArrayType(String type) {
        List<String> specification = specificationOfType(type);
        String recordType = new ExtractRecordTypeName().please(specification);
        if (recordType !=null ) {
            return recordType;
        }

        String message = "Not an array type or unknown type " + type + "\n";
        message += " specification found:\n" + specification + "\n";
        throw new RuntimeException(message);
    }

    public List<String> customTypesOfFields(String type) {
        try {
            ArrayList<String> types = new ArrayList<>();
            List<String> typeSpecification = specificationOfType(type);
            Parameters parameters = new ExtractParameters().please(typeSpecification);
            for (int index = 0; index < parameters.count(); index++) {
                String specification = parameters.get(index).toLowerCase();
                specification = specification.replaceAll("\\s+", " ");
                if (specification.indexOf(" in ") != -1) {
                    specification = specification.replace(" in ", " ");
                }
                String[] parts = specification.trim().split("\\s");
                String candidate = parts[1];
                if (isCustomType(candidate)) {
                    types.add(candidate);
                }
            }
            return types;
        }
        catch (RuntimeException e) {
            String message = "Something went wrong with type " + type;
            throw new RuntimeException(message, e);
        }
    }

    private List<String> specificationOfType(String type) {
        for (int i=0; i<typeSpecifications.size(); i++) {
            List<String> typeSpecification = typeSpecifications.get(i);
            String typeName = new ExtractTypeName().please(typeSpecification);
            if (typeName.equalsIgnoreCase(type)) {
                return typeSpecification;
            }
        }
        String message = "Unknown type: " + type + "\n" + typeSpecifications.size() +" known types:\n";
        for (int i=0; i<typeSpecifications.size(); i++) {
            List<String> typeSpecification = typeSpecifications.get(i);
            String typeName = new ExtractTypeName().please(typeSpecification);
            message += (typeName + "\n");
        }
        throw new RuntimeException(message);
    }
}
