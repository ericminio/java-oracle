package ericminio.javaoracle.demos.custom;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

public class ReturningComplexType {

    private Connection connection;

    public ReturningComplexType(Connection connection) throws SQLException {
        this.connection = connection;
        connection.getTypeMap().put(ComplexType.NAME, ComplexType.class);
        connection.getTypeMap().put(CustomType.NAME, CustomType.class);
    }

    public ComplexType getField() throws SQLException {
        CallableStatement statement = connection.prepareCall("{ ? = call returning_complex_type.get_field() }");
        statement.registerOutParameter(1, Types.STRUCT, "COMPLEX_TYPE");
        statement.executeUpdate();
        Object data = statement.getObject(1);

        return (ComplexType) data;
    }
}
