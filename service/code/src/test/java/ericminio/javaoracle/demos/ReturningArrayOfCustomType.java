package ericminio.javaoracle.demos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ReturningArrayOfCustomType {

    private Connection connection;

    public ReturningArrayOfCustomType(Connection connection) throws SQLException {
        this.connection = connection;
        connection.getTypeMap().put(CustomType.NAME, CustomType.class);
    }

    public ArrayOfCustomType getValue() throws SQLException {
        PreparedStatement statement = connection.prepareStatement("select returning_array_of_custom_type.get_value() from dual");
        ResultSet resultSet = statement.executeQuery();
        resultSet.next();

        return ArrayOfCustomType.with((Object[]) resultSet.getArray(1).getArray());
    }

}
