package ericminio.javaoracle.demos.varchar;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

public class OutVarchar {

    private Connection connection;

    public OutVarchar(Connection connection) {
        this.connection = connection;
    }

    public BigDecimal getValue(BigDecimal input, String[] value) throws SQLException {
        CallableStatement statement = connection.prepareCall("{ ? = call out_varchar.get_value(?, ?) }");
        statement.registerOutParameter(1, Types.NUMERIC);
        statement.setBigDecimal(2, input);
        statement.registerOutParameter(3, Types.VARCHAR);
        statement.executeUpdate();
        Object data = statement.getObject(1);
        Object outValue = statement.getObject(3);
        value[0] = (String) outValue;

        return (BigDecimal) data;
    }

}
