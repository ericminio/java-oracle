package ericminio.javaoracle.demos;

import java.sql.*;

public class FunctionReturningArrayOfCustomType {

    private Connection connection;

    public FunctionReturningArrayOfCustomType(Connection connection) throws SQLException {
        this.connection = connection;
        connection.getTypeMap().put(CustomType.NAME, CustomType.class);
    }

    public ArrayOfCustomType getValue() throws SQLException {
        CallableStatement statement = connection.prepareCall("select returning_array_of_custom_type.get_value() from dual");
        ResultSet resultSet = statement.executeQuery();
        resultSet.next();

        return ArrayOfCustomType.with((Object[]) resultSet.getArray(1).getArray());
    }
}
