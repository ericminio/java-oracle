package ericminio.javaoracle.demos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class FunctionTakingDate {

    private Connection connection;

    public FunctionTakingDate(Connection connection) {
        this.connection = connection;
    }

    public Date getValue(Date input) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("select function_taking_date.get_value(?) from dual");
        statement.setTimestamp(1, new java.sql.Timestamp(input.getTime()));
        ResultSet resultSet = statement.executeQuery();
        resultSet.next();

        return new Date( ((java.sql.Timestamp) resultSet.getObject(1)).getTime() );
    }

}