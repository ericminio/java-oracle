package ericminio.javaoracle.demos.number;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

public class ReturningNumber {

    private Connection connection;

    public ReturningNumber(Connection connection) {
        this.connection = connection;
    }

    public BigDecimal getValue() throws SQLException {
        CallableStatement statement = connection.prepareCall("{ ? = call returning_number.get_value() }");
        statement.registerOutParameter(1, Types.NUMERIC);
        statement.execute();
        Object data = statement.getObject(1);

        return (BigDecimal) data;
    }

}