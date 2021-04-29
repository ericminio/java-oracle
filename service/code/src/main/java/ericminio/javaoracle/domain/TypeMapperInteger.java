package ericminio.javaoracle.domain;

public class TypeMapperInteger implements TypeMapper {
    @Override
    public String resultSetGetter() {
        return "getInt";
    }

    @Override
    public String sqlStatementSetter() {
        return "setInt";
    }

    @Override
    public String javaType() {
        return "Integer";
    }

    @Override
    public String sqlInputRead() {
        return "readInt";
    }

    @Override
    public String sqlOutputWrite() {
        return "writeInt";
    }
}
