package ericminio.javaoracle.demos;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ReturningNumber {

    private Connection connection;

    public ReturningNumber(Connection connection) {
        this.connection = connection;
    }

    public BigDecimal getValue() throws SQLException {
        PreparedStatement statement = connection.prepareStatement("select returning_number.get_value() from dual");
        ResultSet resultSet = statement.executeQuery();
        resultSet.next();

        return (BigDecimal) resultSet.getObject(1);
    }

}