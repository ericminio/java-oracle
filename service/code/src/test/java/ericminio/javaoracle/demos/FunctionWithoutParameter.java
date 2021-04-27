package ericminio.javaoracle.demos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FunctionWithoutParameter {

    private Connection connection;

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public Integer getValue() throws SQLException {
        PreparedStatement statement = connection.prepareStatement("select function_without_parameter.get_value() from dual");
        ResultSet resultSet = statement.executeQuery();
        resultSet.next();

        return resultSet.getInt(1);
    }

}