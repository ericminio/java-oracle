package ericminio.javaoracle.demos.number;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

public class TakingNumber {

    private Connection connection;

    public TakingNumber(Connection connection) {
        this.connection = connection;
    }

    public BigDecimal getValue(BigDecimal input) throws SQLException {
        CallableStatement statement = connection.prepareCall("{ ? = call taking_number.get_value(?) }");
        statement.registerOutParameter(1, Types.NUMERIC);
        statement.setBigDecimal(2, input);
        statement.execute();
        Object data = statement.getObject(1);

        return (BigDecimal) data;
    }

}