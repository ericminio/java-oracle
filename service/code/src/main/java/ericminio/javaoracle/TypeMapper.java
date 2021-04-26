package ericminio.javaoracle;

public class TypeMapper {

    public String typeOf(String type) {
        if ("INTEGER".equalsIgnoreCase(type)) {
            return "Types.INTEGER";
        }

        throw new RuntimeException(("Teach me about type " + type));
    }

    public String accessorOf(String type) {
        if ("INTEGER".equalsIgnoreCase(type)) {
            return "getInt";
        }

        throw new RuntimeException(("Teach me about type " + type));
    }
}
