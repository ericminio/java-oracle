package ericminio.javaoracle;

public class TypeMapperFactory {

    public TypeMapper of(String incoming) {
        if ("INTEGER".equalsIgnoreCase(incoming)) {
            return new TypeMapperInteger();
        }
        if ("VARCHAR2".equalsIgnoreCase(incoming)) {
            return new TypeMapperString();
        }

        throw new RuntimeException(("Teach me about type " + incoming));
    }
}
