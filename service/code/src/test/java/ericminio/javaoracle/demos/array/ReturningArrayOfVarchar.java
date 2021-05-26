package ericminio.javaoracle.demos.array;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

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
