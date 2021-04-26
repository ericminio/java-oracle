package ericminio.javaoracle.demos;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

public class FunctionWithParameter {

    private Connection connection;

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public int countByType(String value) throws SQLException {
        CallableStatement statement = connection.prepareCall("{? = call function_with_parameter.count_by_type(?)}");
        statement.setString(2, value);
        statement.registerOutParameter(1, Types.INTEGER);
        statement.execute();

        return statement.getInt(1);
    }

    public int countByLabel(String value) throws SQLException {
        CallableStatement statement = connection.prepareCall("{? = call function_with_parameter.count_by_label(?)}");
        statement.setString(2, value);
        statement.registerOutParameter(1, Types.INTEGER);
        statement.execute();

        return statement.getInt(1);
    }

}