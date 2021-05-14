package ericminio.javaoracle.demos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class ReturningDate {

    private Connection connection;

    public ReturningDate(Connection connection) {
        this.connection = connection;
    }

    public Date getValue() throws SQLException {
        PreparedStatement statement = connection.prepareStatement("select returning_date.get_value() from dual");
        ResultSet resultSet = statement.executeQuery();
        resultSet.next();
        Object data = resultSet.getObject(1);

        return data == null ? null : new Date( ((java.sql.Timestamp) data).getTime() );
    }

}