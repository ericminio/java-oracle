package ericminio.javaoracle.demos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FunctionTakingCustomType {

    private Connection connection;

    public FunctionTakingCustomType(Connection connection) throws SQLException {
        this.connection = connection;
        connection.getTypeMap().put(CustomType.NAME, CustomType.class);
    }

    public CustomType getValue(CustomType input) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("select function_taking_custom_type.get_value(?) from dual");
        statement.setObject(1, input);
        ResultSet resultSet = statement.executeQuery();
        resultSet.next();

        return (CustomType) resultSet.getObject(1);
    }

}