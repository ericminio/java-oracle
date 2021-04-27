package ericminio.javaoracle;

public class TypeMapper {

    public String typeOf(String type) {
        if ("INTEGER".equalsIgnoreCase(type)) {
            return "Types.INTEGER";
        }
        if ("VARCHAR2".equalsIgnoreCase(type)) {
            return "Types.VARCHAR";
        }

        throw new RuntimeException(("Teach me about type " + type));
    }

    public String getterOf(String type) {
        if ("INTEGER".equalsIgnoreCase(type)) {
            return "getInt";
        }
        if ("VARCHAR2".equalsIgnoreCase(type)) {
            return "getString";
        }

        throw new RuntimeException(("Teach me about type " + type));
    }

    public String setterOf(String type) {
        if ("INTEGER".equalsIgnoreCase(type)) {
            return "setInt";
        }
        if ("VARCHAR2".equalsIgnoreCase(type)) {
            return "setString";
        }

        throw new RuntimeException(("Teach me about type " + type));
    }

    public String javaTypeOf(String type) {
        if ("INTEGER".equalsIgnoreCase(type)) {
            return "int";
        }
        if ("VARCHAR2".equalsIgnoreCase(type)) {
            return "String";
        }

        throw new RuntimeException(("Teach me about type " + type));
    }
}
