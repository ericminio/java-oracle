package ericminio.javaoracle.demos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TakingVarchar {

    private Connection connection;

    public TakingVarchar(Connection connection) {
        this.connection = connection;
    }

    public String getValue(String input) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("select taking_varchar.get_value(?) from dual");
        statement.setString(1, input);
        ResultSet resultSet = statement.executeQuery();
        resultSet.next();
        Object data = resultSet.getObject(1);

        return (String) data;
    }

}