package ericminio.javaoracle.demos;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FunctionTakingNumber {

    private Connection connection;

    public FunctionTakingNumber(Connection connection) {
        this.connection = connection;
    }

    public BigDecimal getValue(BigDecimal input) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("select function_taking_number.get_value(?) from dual");
        statement.setBigDecimal(1, input);
        ResultSet resultSet = statement.executeQuery();
        resultSet.next();

        return (BigDecimal) resultSet.getObject(1);
    }

}