package ericminio.javaoracle.demos;

import java.sql.*;

public class FunctionReturningArrayOfVarchar {

    private Connection connection;

    public FunctionReturningArrayOfVarchar(Connection connection) {
        this.connection = connection;
    }

    public ArrayOfVarchar getValue() throws SQLException {
        CallableStatement statement = connection.prepareCall("select returning_array_of_varchar.get_value() from dual");
        ResultSet resultSet = statement.executeQuery();
        resultSet.next();

        return ArrayOfVarchar.with((Object[]) resultSet.getArray(1).getArray());
    }
}
