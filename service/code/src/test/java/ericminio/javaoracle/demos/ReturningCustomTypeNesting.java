package ericminio.javaoracle.demos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ReturningCustomTypeNesting {

    private Connection connection;

    public ReturningCustomTypeNesting(Connection connection) throws SQLException {
        this.connection = connection;
        connection.getTypeMap().put(CustomTypeNesting.NAME, CustomTypeNesting.class);
        connection.getTypeMap().put(CustomType.NAME, CustomType.class);
    }

    public CustomTypeNesting getValue() throws SQLException {
        PreparedStatement statement = connection.prepareStatement("select returning_custom_type_nesting.get_value() from dual");
        ResultSet resultSet = statement.executeQuery();
        resultSet.next();
        Object data = resultSet.getObject(1);

        return (CustomTypeNesting) data;
    }

}
