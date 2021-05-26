package ericminio.javaoracle.demos.custom;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

public class TakingCustomType {

    private Connection connection;

    public TakingCustomType(Connection connection) throws SQLException {
        this.connection = connection;
        connection.getTypeMap().put(CustomType.NAME, CustomType.class);
    }

    public CustomType getValue(CustomType input) throws SQLException {
        CallableStatement statement = connection.prepareCall("{ ? = call taking_custom_type.get_value(?) }");
        statement.registerOutParameter(1, Types.STRUCT, "CUSTOM_TYPE");
        statement.setObject(2, input);
        statement.executeUpdate();
        Object data = statement.getObject(1);

        return (CustomType) data;
    }

}