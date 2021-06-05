package ericminio.javaoracle.demos.number;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

public class OutNumber {

    private Connection connection;

    public OutNumber() {
    }

    public OutNumber(Connection connection) {
        this.setConnection(connection);
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public BigDecimal getValue(BigDecimal input, BigDecimal[] value) throws SQLException {
        CallableStatement statement = connection.prepareCall("{ ? = call out_number.get_value(?, ?) }");
        statement.registerOutParameter(1, Types.NUMERIC);
        statement.setBigDecimal(2, input);
        statement.registerOutParameter(3, Types.NUMERIC);
        statement.execute();
        Object data = statement.getObject(1);
        Object outValue = statement.getObject(3);
        value[0] = (BigDecimal) outValue;

        return (BigDecimal) data;
    }

}
