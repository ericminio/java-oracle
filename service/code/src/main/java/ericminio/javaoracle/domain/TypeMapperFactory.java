package ericminio.javaoracle.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        if (isCustomType(incoming)) {
            if (isArrayType(incoming)) {
                return new TypeMapperArrayType(incoming);
            }
            return new TypeMapperCustomType(incoming);
        }

        throw new RuntimeException(("Teach me about type " + incoming));
    }

    public static boolean isCustomType(String type) {
        return type.indexOf("_") != -1;
    }

    public boolean isArrayType(String type) {
        for (int i=0; i<typeSpecifications.size(); i++) {
            String statement = getTypeSpecification(i);
            if (statement.indexOf("type " + type.toLowerCase()) != -1) {
                if (statement.indexOf("as object") != -1) {
                    return false;
                }
                else {
                    return true;
                }
            }
        }
        return false;
    }

    public String recordTypeOfArrayType(String type) {
        Pattern pattern = Pattern.compile("of (.*)");
        for (int i=0; i<typeSpecifications.size(); i++) {
            String statement = getTypeSpecification(i);
            if (statement.indexOf("type " + type.toLowerCase()) != -1) {
                Matcher matcher = pattern.matcher(statement);
                if (matcher.find()) {
                    return matcher.group(1).replace(";", "");
                }
            }
        }
        throw new RuntimeException("Not an array type or unknwn type " + type);
    }

    private String getTypeSpecification(int i) {
        List<String> typeSpecification = typeSpecifications.get(i);
        return new JoinWith(" ").please(typeSpecification).trim().toLowerCase();
    }

    public List<String> customTypesOfFields(String type) {
        ArrayList<String> types = new ArrayList<>();
        List<String> typeSpecification = specificationOfType(type);
        Parameters parameters = new ExtractParameters().please(typeSpecification);
        for (int index = 0; index< parameters.count(); index++) {
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

    private List<String> specificationOfType(String type) {
        for (int i=0; i<typeSpecifications.size(); i++) {
            List<String> typeSpecification = typeSpecifications.get(i);
            String typeName = new ExtractTypeName().please(typeSpecification);
            if (typeName.equalsIgnoreCase(type)) {
                return typeSpecification;
            }
        }
        throw new RuntimeException("unknown type: " + type);
    }
}
