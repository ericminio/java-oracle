package ericminio.javaoracle.domain;

public class TypeMapperDate implements TypeMapper {

    @Override
    public String sqlStatementSetter() {
        return "setObject";
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
