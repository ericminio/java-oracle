package ericminio.javaoracle.domain;

import ericminio.javaoracle.support.PascalCase;

public class TypeMapperCustomType implements TypeMapper {

    private String type;

    public TypeMapperCustomType(String type) {
        this.type = type;
    }

    @Override
    public String sqlStatementSetter() {
        return "setObject";
    }

    @Override
    public String javaType() {
        return new PascalCase().please(this.type);
    }

    @Override
    public String sqlInputRead() {
        return "readObject";
    }

    @Override
    public String sqlOutputWrite() {
        return "writeObject";
    }

    @Override
    public String methodReturnStatement() {
        return "return (" + new PascalCase().please(this.type) + ") resultSet.getObject(1);";
    }
}
