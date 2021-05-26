package ericminio.javaoracle.demos.date;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Date;

public class TakingDate {

    private Connection connection;

    public TakingDate(Connection connection) {
        this.connection = connection;
    }

    public Date getValue(Date input) throws SQLException {
        CallableStatement statement = connection.prepareCall("{ ? = call taking_date.get_value(?) }");
        statement.registerOutParameter(1, Types.TIMESTAMP);
        statement.setTimestamp(2, new java.sql.Timestamp(input.getTime()));
        statement.execute();
        Object data = statement.getObject(1);

        return data == null ? null : new Date( ((java.sql.Timestamp) data).getTime() );
    }

}