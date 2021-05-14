package ericminio.javaoracle.demos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ReturningVarchar {

    private Connection connection;

    public ReturningVarchar(Connection connection) {
        this.connection = connection;
    }

    public String getValue() throws SQLException {
        PreparedStatement statement = connection.prepareStatement("select returning_varchar.get_value() from dual");
        ResultSet resultSet = statement.executeQuery();
        resultSet.next();
        Object data = resultSet.getObject(1);

        return (String) data;
    }

}