package ericminio.javaoracle.demos;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

public class FunctionWithoutParameter {

    private Connection connection;

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public int getValue() throws SQLException {
        CallableStatement statement = connection.prepareCall("{? = call function_without_parameter.get_value()}");
        statement.registerOutParameter(1, Types.INTEGER);
        statement.execute();

        return statement.getInt(1);
    }

}