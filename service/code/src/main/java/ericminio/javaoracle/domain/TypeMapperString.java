package ericminio.javaoracle.domain;

public class TypeMapperString implements TypeMapper {

    @Override
    public String functionParameterSettingStatement() {
        return "statement.setString(index, field);";
    }

    @Override
    public String javaType() {
        return "String";
    }

    @Override
    public String sqlInputRead() {
        return "stream.readString()";
    }

    @Override
    public String sqlOutputWrite() {
        return "stream.writeString(this.getField());";
    }

    @Override
    public String methodReturnStatement() {
        return "return (String) resultSet.getObject(1);";
    }
}
