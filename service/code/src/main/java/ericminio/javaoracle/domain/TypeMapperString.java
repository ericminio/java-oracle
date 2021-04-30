package ericminio.javaoracle.domain;

public class TypeMapperString implements TypeMapper {

    @Override
    public String sqlStatementSetter() {
        return "setString";
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

    @Override
    public String methodReturnStatement() {
        return "return (String) resultSet.getObject(1);";
    }
}
