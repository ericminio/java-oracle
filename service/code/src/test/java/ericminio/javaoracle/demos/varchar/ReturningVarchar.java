package ericminio.javaoracle.demos.varchar;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

public class ReturningVarchar {

    private Connection connection;

    public ReturningVarchar(Connection connection) {
        this.connection = connection;
    }

    public String getValue() throws SQLException {
        CallableStatement statement = connection.prepareCall("{ ? = call returning_varchar.get_value() }");
        statement.registerOutParameter(1, Types.VARCHAR);
        statement.executeUpdate();
        Object data = statement.getObject(1);

        return (String) data;
    }

}