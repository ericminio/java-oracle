package ericminio.javaoracle.domain;

public class TypeMapperDate implements TypeMapper {

    @Override
    public String functionParameterSettingStatement() {
        return "statement.setTimestamp(index, new java.sql.Timestamp(field.getTime()));";
    }

    @Override
    public String javaType() {
        return "Date";
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
        return "return data == null ? null : new Date( ((java.sql.Timestamp) data).getTime() );";
    }
}
