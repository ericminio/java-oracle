package ericminio.javaoracle.demos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FunctionWithParameterString {

    private Connection connection;

    public FunctionWithParameterString(Connection connection) {
        this.connection = connection;
    }

    public String getValue(String input) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("select function_with_parameter_string.get_value(?) from dual");
        statement.setString(1, input);
        ResultSet resultSet = statement.executeQuery();
        resultSet.next();

        return (String) resultSet.getObject(1);
    }

}