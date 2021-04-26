package ericminio.javaoracle.demos;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

public class Exploration {

    private Connection connection;

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public int getEventCount() throws SQLException {
        CallableStatement statement = connection.prepareCall("{? = call exploration.get_event_count()}");
        statement.registerOutParameter(1, Types.INTEGER);
        statement.execute();

        return statement.getInt(1);
    }

}