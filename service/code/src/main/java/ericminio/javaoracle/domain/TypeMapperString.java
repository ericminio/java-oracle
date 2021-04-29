package ericminio.javaoracle.domain;

public class TypeMapperString implements TypeMapper {
    @Override
    public String getter() {
        return "getString";
    }

    @Override
    public String setter() {
        return "setString";
    }

    @Override
    public String sqlType() {
        return "Types.VARCHAR";
    }

    @Override
    public String javaType() {
        return "String";
    }

    @Override
    public String sqlInputRead() {
        return "readString";
    }

    @Override
    public String sqlOutputWrite() {
        return "writeString";
    }
}
