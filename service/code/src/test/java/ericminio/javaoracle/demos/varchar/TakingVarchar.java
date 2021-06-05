package ericminio.javaoracle.demos.varchar;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

public class TakingVarchar {

    private Connection connection;

    public TakingVarchar() {
    }

    public TakingVarchar(Connection connection) {
        this.setConnection(connection);
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public String getValue(String input) throws SQLException {
        CallableStatement statement = connection.prepareCall("{ ? = call taking_varchar.get_value(?) }");
        statement.registerOutParameter(1, Types.VARCHAR);
        statement.setString(2, input);
        statement.execute();
        Object data = statement.getObject(1);

        return (String) data;
    }

}