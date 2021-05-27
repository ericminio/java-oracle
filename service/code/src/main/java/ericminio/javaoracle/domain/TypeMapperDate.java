package ericminio.javaoracle.domain;

public class TypeMapperDate implements TypeMapper {

    @Override
    public String functionParameterSettingStatement() {
        return "statement.setTimestamp(index, field == null ? null : new java.sql.Timestamp(field.getTime()));";
    }

    @Override
    public String javaType() {
        return "Date";
    }

    @Override
    public String functionParameterOutType() {
        return "Types.TIMESTAMP";
    }

    @Override
    public String sqlInputRead() {
        return "buildDateOrNull(stream.readTimestamp())";
    }

    @Override
    public String sqlOutputWrite() {
        return "stream.writeTimestamp(new java.sql.Timestamp(this.getField().getTime()));";
    }

    @Override
    public String methodReturnStatement() {
        return "return " + cast();
    }

    @Override
    public String cast() {
        return "data == null ? null : new Date( ((java.sql.Timestamp) data).getTime() );";
    }
}
