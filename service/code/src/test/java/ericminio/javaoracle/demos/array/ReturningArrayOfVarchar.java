package ericminio.javaoracle.demos.array;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

public class ReturningArrayOfVarchar {

    private Connection connection;

    public ReturningArrayOfVarchar(Connection connection) {
        this.connection = connection;
    }

    public ArrayOfVarchar getValue() throws SQLException {
        CallableStatement statement = connection.prepareCall("{ ? = call returning_array_of_varchar.get_value() }");
        statement.registerOutParameter(1, Types.ARRAY, "ARRAY_OF_VARCHAR");
        statement.executeUpdate();
        Object data = statement.getObject(1);

        return ArrayOfVarchar.with((Object[]) ((java.sql.Array) data).getArray());
    }

}
