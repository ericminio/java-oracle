package ericminio.javaoracle.demos.custom;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ReturningArrayEmpty {

    private Connection connection;

    public ReturningArrayEmpty(Connection connection) throws SQLException {
        this.connection = connection;
        connection.getTypeMap().put(CustomType.NAME, CustomType.class);
    }

    public ArrayOfCustomType getValue() throws SQLException {
        PreparedStatement statement = connection.prepareStatement("select returning_array_empty.get_value() from dual");
        ResultSet resultSet = statement.executeQuery();
        resultSet.next();
        Object data = resultSet.getObject(1);

        return ArrayOfCustomType.with((Object[]) ((java.sql.Array) data).getArray());
    }

}
