package ericminio.javaoracle.demos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class FunctionReturningDate {

    private Connection connection;

    public FunctionReturningDate(Connection connection) {
        this.connection = connection;
    }

    public Date getValue() throws SQLException {
        PreparedStatement statement = connection.prepareStatement("select function_returning_date.get_value() from dual");
        ResultSet resultSet = statement.executeQuery();
        resultSet.next();

        return new Date( ((java.sql.Timestamp) resultSet.getObject(1)).getTime() );
    }

}