package ericminio.javaoracle.demos.custom;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

public class ReturningCustomTypeDateFieldNull {

    private Connection connection;

    public ReturningCustomTypeDateFieldNull(Connection connection) throws SQLException {
        this.connection = connection;
        connection.getTypeMap().put(CustomType.NAME, CustomType.class);
    }

    public CustomType getValue() throws SQLException {
        CallableStatement statement = connection.prepareCall("{ ? = call custom_type_date_null.get_value() }");
        statement.registerOutParameter(1, Types.STRUCT, "CUSTOM_TYPE");
        statement.executeUpdate();
        Object data = statement.getObject(1);

        return (CustomType) data;
    }

}
