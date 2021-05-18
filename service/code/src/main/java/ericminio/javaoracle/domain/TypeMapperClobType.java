package ericminio.javaoracle.domain;

public class TypeMapperClobType implements TypeMapper {

    @Override
    public String functionParameterSettingStatement() {
        return "statement.setClob(index, field);";
    }

    @Override
    public String javaType() {
        return "Clob";
    }

    @Override
    public String sqlInputRead() {
        return "stream.readClob()";
    }

    @Override
    public String sqlOutputWrite() {
        return "stream.writeClob(this.getField());";
    }

    @Override
    public String methodReturnStatement() {
        return "return (Clob) data;";
    }
}
