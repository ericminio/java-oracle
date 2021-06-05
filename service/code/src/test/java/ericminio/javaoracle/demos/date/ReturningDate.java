package ericminio.javaoracle.demos.date;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Date;

public class ReturningDate {

    private Connection connection;

    public ReturningDate() {
    }

    public ReturningDate(Connection connection) {
        this.setConnection(connection);
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public Date getValue() throws SQLException {
        CallableStatement statement = connection.prepareCall("{ ? = call returning_date.get_value() }");
        statement.registerOutParameter(1, Types.TIMESTAMP);
        statement.execute();
        Object data = statement.getObject(1);

        return data == null ? null : new Date( ((java.sql.Timestamp) data).getTime() );
    }

}