package ericminio.javaoracle.demos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FunctionReturningVarchar {

    private Connection connection;

    public FunctionReturningVarchar(Connection connection) {
        this.connection = connection;
    }

    public String getValue() throws SQLException {
        PreparedStatement statement = connection.prepareStatement("select function_returning_varchar.get_value() from dual");
        ResultSet resultSet = statement.executeQuery();
        resultSet.next();

        return (String) resultSet.getObject(1);
    }

}