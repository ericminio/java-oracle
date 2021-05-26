package ericminio.javaoracle.demos;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

public class TakingNoParameter {

    private Connection connection;

    public TakingNoParameter(Connection connection) {
        this.connection = connection;
    }

    public BigDecimal getValue() throws SQLException {
        CallableStatement statement = connection.prepareCall("{ ? = call taking_no_parameter.get_value() }");
        statement.registerOutParameter(1, Types.NUMERIC);
        statement.execute();
        Object data = statement.getObject(1);

        return (BigDecimal) data;
    }

}