package ericminio.javaoracle.demos;

import java.sql.*;

public class ReturningArrayOfVarchar {

    private Connection connection;

    public ReturningArrayOfVarchar(Connection connection) {
        this.connection = connection;
    }

    public ArrayOfVarchar getValue() throws SQLException {
        CallableStatement statement = connection.prepareCall("select returning_array_of_varchar.get_value() from dual");
        ResultSet resultSet = statement.executeQuery();
        resultSet.next();
        Object candidate = resultSet.getObject(1);

        return ArrayOfVarchar.with((Object[]) ((java.sql.Array) candidate).getArray());
    }
}
