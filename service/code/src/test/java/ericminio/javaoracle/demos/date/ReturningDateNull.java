package ericminio.javaoracle.demos.date;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Date;

public class ReturningDateNull {

    private Connection connection;

    public ReturningDateNull(Connection connection) {
        this.connection = connection;
    }

    public Date getValue() throws SQLException {
        CallableStatement statement = connection.prepareCall("{ ? = call returning_date_null.get_value() }");
        statement.registerOutParameter(1, Types.TIMESTAMP);
        statement.execute();
        Object data = statement.getObject(1);

        return data == null ? null : new Date( ((java.sql.Timestamp) data).getTime() );
    }

}