package ericminio.javaoracle.demos.date;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class ReturningDateNull {

    private Connection connection;

    public ReturningDateNull(Connection connection) {
        this.connection = connection;
    }

    public Date getValue() throws SQLException {
        PreparedStatement statement = connection.prepareStatement("select returning_date_null.get_value() from dual");
        ResultSet resultSet = statement.executeQuery();
        resultSet.next();
        Object data = resultSet.getObject(1);

        return data == null ? null : new Date( ((java.sql.Timestamp) data).getTime() );
    }

}