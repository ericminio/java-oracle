package ericminio.javaoracle.demos.custom;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

public class ReturningCustomTypeNesting {

    private Connection connection;

    public ReturningCustomTypeNesting(Connection connection) throws SQLException {
        this.connection = connection;
        connection.getTypeMap().put(CustomTypeNesting.NAME, CustomTypeNesting.class);
        connection.getTypeMap().put(CustomType.NAME, CustomType.class);
    }

    public CustomTypeNesting getValue() throws SQLException {
        CallableStatement statement = connection.prepareCall("{ ? = call returning_custom_type_nesting.get_value() }");
        statement.registerOutParameter(1, Types.STRUCT, "CUSTOM_TYPE_NESTING");
        statement.execute();
        Object data = statement.getObject(1);

        return (CustomTypeNesting) data;
    }

}
