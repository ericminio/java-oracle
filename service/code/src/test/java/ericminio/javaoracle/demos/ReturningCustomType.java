package ericminio.javaoracle.demos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ReturningCustomType {

    private Connection connection;

    public ReturningCustomType(Connection connection) throws SQLException {
        this.connection = connection;
        connection.getTypeMap().put(CustomType.NAME, CustomType.class);
    }

    public CustomType getValue() throws SQLException {
        PreparedStatement statement = connection.prepareStatement("select returning_custom_type.get_value() from dual");
        ResultSet resultSet = statement.executeQuery();
        resultSet.next();
        Object data = resultSet.getObject(1);

        return (CustomType) data;
    }

}
