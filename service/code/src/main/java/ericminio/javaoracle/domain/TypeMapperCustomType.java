package ericminio.javaoracle.domain;

import ericminio.javaoracle.support.PascalCase;

public class TypeMapperCustomType implements TypeMapper {

    private String type;

    public TypeMapperCustomType(String type) {
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
        return "Types.STRUCT, \""+ this.type.toUpperCase() + "\"";
    }

    @Override
    public String sqlInputRead() {
        return "(" + new PascalCase().please(this.type) + ") stream.readObject()";
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
        return "(" + new PascalCase().please(this.type) + ") data;";
    }
}
