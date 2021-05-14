package ericminio.javaoracle.demos;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TakingNumber {

    private Connection connection;

    public TakingNumber(Connection connection) {
        this.connection = connection;
    }

    public BigDecimal getValue(BigDecimal input) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("select taking_number.get_value(?) from dual");
        statement.setBigDecimal(1, input);
        ResultSet resultSet = statement.executeQuery();
        resultSet.next();
        Object data = resultSet.getObject(1);

        return (BigDecimal) data;
    }

}