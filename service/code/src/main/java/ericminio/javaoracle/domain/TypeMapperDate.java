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
        return "new Date(stream.readTimestamp().getTime())";
    }

    @Override
    public String sqlOutputWrite() {
        return "stream.writeTimestamp(new java.sql.Timestamp(this.getField().getTime()));";
    }

    @Override
    public String methodReturnStatement() {
        return "return new Date( ((java.sql.Timestamp) resultSet.getObject(1)).getTime() );";
    }
}
