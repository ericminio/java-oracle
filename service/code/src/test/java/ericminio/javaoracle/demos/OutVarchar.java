package ericminio.javaoracle.demos;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

public class OutVarchar {

    private Connection connection;

    public OutVarchar(Connection connection) {
        this.connection = connection;
    }

    public String getValue() throws SQLException {
        CallableStatement statement = connection.prepareCall("{ call out_varchar.get_value(?) }");
        statement.registerOutParameter(1, Types.VARCHAR);
        statement.executeUpdate();
        Object data = statement.getObject(1);

        return (String) data;
    }
}
