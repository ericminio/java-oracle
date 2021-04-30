package ericminio.javaoracle.domain;

public class TypeMapperFactory {

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
            return new TypeMapperCustomType(incoming);
        }

        throw new RuntimeException(("Teach me about type " + incoming));
    }

    public static boolean isCutomType(String type) {
        return type.indexOf("_") != -1;
    }
}
