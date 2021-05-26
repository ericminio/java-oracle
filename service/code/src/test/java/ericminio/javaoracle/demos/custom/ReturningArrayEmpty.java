package ericminio.javaoracle.demos.custom;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

public class ReturningArrayEmpty {

    private Connection connection;

    public ReturningArrayEmpty(Connection connection) throws SQLException {
        this.connection = connection;
        connection.getTypeMap().put(CustomType.NAME, CustomType.class);
    }

    public ArrayOfCustomType getValue() throws SQLException {
        CallableStatement statement = connection.prepareCall("{ ? = call returning_array_empty.get_value() }");
        statement.registerOutParameter(1, Types.ARRAY, "ARRAY_OF_CUSTOM_TYPE");
        statement.execute();
        Object data = statement.getObject(1);

        return ArrayOfCustomType.with((Object[]) ((java.sql.Array) data).getArray());
    }

}
