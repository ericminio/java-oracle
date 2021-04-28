package ericminio.javaoracle.demos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FunctionReturningCustomTypeNesting {

    private Connection connection;

    public FunctionReturningCustomTypeNesting(Connection connection) {
        this.connection = connection;
    }

    public CustomTypeNesting getField() throws SQLException {
        connection.getTypeMap().put(CustomTypeNesting.NAME, CustomTypeNesting.class);
        connection.getTypeMap().put(CustomTypeNested.NAME, CustomTypeNested.class);
        PreparedStatement statement = connection.prepareStatement("select returning_custom_type_nesting.get_field() from dual");
        ResultSet resultSet = statement.executeQuery();
        resultSet.next();

        return (CustomTypeNesting) resultSet.getObject(1);
    }
}
