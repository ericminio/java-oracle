package ericminio.javaoracle.demos;

import oracle.jdbc.OracleTypes;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

public class FunctionReturningCustomType {

    private Connection connection;

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public CustomType getValue() throws SQLException {
        connection.getTypeMap().put(CustomType.NAME, CustomType.class);
        CallableStatement statement = connection.prepareCall("{? = call function_returning_custom_type.get_value()}");
        statement.registerOutParameter(1, OracleTypes.STRUCT, CustomType.NAME);
        statement.execute();

        return (CustomType) statement.getObject(1);
    }
}
