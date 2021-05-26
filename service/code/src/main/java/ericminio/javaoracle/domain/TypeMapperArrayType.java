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
    public String functionParameterOutType() {
        return "Types.ARRAY, \"" + this.type.toUpperCase() + "\"";
    }

    @Override
    public String sqlInputRead() {
        return new PascalCase().please(this.type) + ".with((Object[]) stream.readArray().getArray())";
    }

    @Override
    public String sqlOutputWrite() {
        return "stream.writeObject(this.getField());";
    }

    @Override
    public String methodReturnStatement() {
        return "return " + cast();
    }

    @Override
    public String cast() {
        return new PascalCase().please(this.type) + ".with((Object[]) ((java.sql.Array) data).getArray());";
    }
}
