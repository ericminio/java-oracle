package ericminio.javaoracle.domain;

import ericminio.javaoracle.support.PascalCase;

public class TypeMapperArrayType implements TypeMapper {

    private String type;

    public TypeMapperArrayType(String type) {
        this.type = type;
    }

    @Override
    public String functionParameterSettingStatement() {
        return "statement.setObject(index, field);";
    }

    @Override
    public String javaType() {
        return new PascalCase().please(this.type);
    }

    @Override
    public String sqlInputRead() {
        return "stream.readObject()";
    }

    @Override
    public String sqlOutputWrite() {
        return "stream.writeObject(this.getField());";
    }

    @Override
    public String methodReturnStatement() {
        return "return " + new PascalCase().please(this.type) + ".with((Object[]) resultSet.getArray(1).getArray());";
    }
}
