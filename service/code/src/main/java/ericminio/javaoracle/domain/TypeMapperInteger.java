package ericminio.javaoracle.domain;

public class TypeMapperInteger implements TypeMapper {
    @Override
    public String getter() {
        return "getInt";
    }

    @Override
    public String setter() {
        return "setInt";
    }

    @Override
    public String sqlType() {
        return "Types.INTEGER";
    }

    @Override
    public String javaType() {
        return "Integer";
    }

    @Override
    public String sqlInputRead() {
        return "readInt";
    }
}
