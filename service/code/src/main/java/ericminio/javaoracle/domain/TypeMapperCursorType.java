package ericminio.javaoracle.domain;

public class TypeMapperCursorType implements TypeMapper {
    private String type;

    public TypeMapperCursorType(String type) {
        this.type = type;
    }

    @Override
    public String functionParameterSettingStatement() {
        return "statement.setObject(index, field);";
    }

    @Override
    public String javaType() {
        return "ResultSet";
    }

    @Override
    public String functionParameterOutType() {
        return "oracle.jdbc.OracleTypes.CURSOR";
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
        return "return " + cast();
    }

    @Override
    public String cast() {
        return "(ResultSet) data;";
    }
}
