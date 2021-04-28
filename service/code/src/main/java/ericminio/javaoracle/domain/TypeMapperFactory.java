package ericminio.javaoracle.domain;

public class TypeMapperFactory {

    public TypeMapper of(String incoming) {
        String normalized = incoming.toUpperCase();
        if (normalized.equalsIgnoreCase("INTEGER")) {
            return new TypeMapperInteger();
        }
        if (normalized.startsWith("VARCHAR2")) {
            return new TypeMapperString();
        }

        throw new RuntimeException(("Teach me about type " + incoming));
    }
}
