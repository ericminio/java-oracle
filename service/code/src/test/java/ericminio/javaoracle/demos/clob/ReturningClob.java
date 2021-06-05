package ericminio.javaoracle.demos.clob;

import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

public class ReturningClob {

    private Connection connection;

    public ReturningClob() {
    }

    public ReturningClob(Connection connection) {
        this.setConnection(connection);
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public Clob getValue() throws SQLException {
        CallableStatement statement = connection.prepareCall("{ ? = call returning_clob.get_value() }");
        statement.registerOutParameter(1, Types.CLOB);
        statement.execute();
        Object data = statement.getObject(1);

        return (Clob) data;
    }

}