package ericminio.javaoracle.demos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ReturningCustomTypeDateFieldNull {

    private Connection connection;

    public ReturningCustomTypeDateFieldNull(Connection connection) throws SQLException {
        this.connection = connection;
        connection.getTypeMap().put(CustomType.NAME, CustomType.class);
    }

    public CustomType getValue() throws SQLException {
        PreparedStatement statement = connection.prepareStatement("select custom_type_date_null.get_value() from dual");
        ResultSet resultSet = statement.executeQuery();
        resultSet.next();
        Object data = resultSet.getObject(1);

        return (CustomType) data;
    }

}