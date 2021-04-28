package ericminio.javaoracle.demos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FunctionReturningCustomType {

    private Connection connection;

    public FunctionReturningCustomType(Connection connection) {
        this.connection = connection;
    }

    public CustomType getValue() throws SQLException {
        connection.getTypeMap().put(CustomType.NAME, CustomType.class);
        PreparedStatement statement = connection.prepareStatement("select function_returning_custom_type.get_value() from dual");
        ResultSet resultSet = statement.executeQuery();
        resultSet.next();

        return (CustomType) resultSet.getObject(1);
    }
}
