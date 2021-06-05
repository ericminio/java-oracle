package ericminio.javaoracle.demos.custom;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

public class ReturningCustomType {

    private Connection connection;

    public ReturningCustomType() {
    }

    public ReturningCustomType(Connection connection) throws SQLException {
        this.setConnection(connection);
    }

    public void setConnection(Connection connection) throws SQLException {
        this.connection = connection;
        connection.getTypeMap().put(CustomType.NAME, CustomType.class);
    }

    public CustomType getValue() throws SQLException {
        CallableStatement statement = connection.prepareCall("{ ? = call returning_custom_type.get_value() }");
        statement.registerOutParameter(1, Types.STRUCT, "CUSTOM_TYPE");
        statement.execute();
        Object data = statement.getObject(1);

        return (CustomType) data;
    }

}
