package ericminio.javaoracle.domain;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TypeMapperFactory {

    private List<List<String>> typeSpecifications;

    public TypeMapperFactory() {
        this(Arrays.asList(Arrays.asList(new String[]{})));
    }

    public TypeMapperFactory(List<List<String>> typeSpecifications) {
        this.typeSpecifications = typeSpecifications;
    }

    public TypeMapper of(String incoming) {
        String normalized = incoming.toUpperCase();
        if (normalized.startsWith("NUMBER")) {
            return new TypeMapperNumber();
        }
        if (normalized.startsWith("VARCHAR2")) {
            return new TypeMapperString();
        }
        if (normalized.startsWith("DATE")) {
            return new TypeMapperDate();
        }
        if (isCutomType(incoming)) {
            if (isArrayType(incoming)) {
                return new TypeMapperArrayType(incoming);
            }
            return new TypeMapperCustomType(incoming);
        }

        throw new RuntimeException(("Teach me about type " + incoming));
    }

    public static boolean isCutomType(String type) {
        return type.indexOf("_") != -1;
    }

    public boolean isArrayType(String type) {
        for (int i=0; i<typeSpecifications.size(); i++) {
            String statement = getTypeSpecification(i);
            if (statement.indexOf("type " + type) != -1) {
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

    private String getTypeSpecification(int i) {
        List<String> typeSpecification = typeSpecifications.get(i);
        String statement = "";
        for (int j = 0; j<typeSpecification.size(); j++) {
            statement += typeSpecification.get(j);
            statement += " ";
        }
        statement = statement.trim();
        return statement.toLowerCase();
    }

    public String recordTypeOfArrayType(String type) {
        Pattern pattern = Pattern.compile("of (.*);");
        for (int i=0; i<typeSpecifications.size(); i++) {
            String statement = getTypeSpecification(i);
            if (statement.indexOf("type " + type) != -1) {
                Matcher matcher = pattern.matcher(statement);
                if (matcher.find()) {
                    return matcher.group(1);
                }
            }
        }
        throw new RuntimeException("Not an array type or unknwn type " + type);
    }
}
