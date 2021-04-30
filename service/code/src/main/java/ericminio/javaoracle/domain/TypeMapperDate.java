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
        return "readDate";
    }

    @Override
    public String sqlOutputWrite() {
        return "writeDate";
    }

    @Override
    public String methodReturnStatement() {
        return "return new Date( ((java.sql.Timestamp) resultSet.getObject(1)).getTime() );";
    }
}
