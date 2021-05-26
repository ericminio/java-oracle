package ericminio.javaoracle.demos.date;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Date;

public class OutDate {

    private Connection connection;

    public OutDate(Connection connection) {
        this.connection = connection;
    }

    public BigDecimal getValue(Date[] value) throws SQLException {
        CallableStatement statement = connection.prepareCall("{ ? = call out_date.get_value(?) }");
        statement.registerOutParameter(1, Types.NUMERIC);
        statement.registerOutParameter(2, Types.TIMESTAMP);
        statement.executeUpdate();
        Object data = statement.getObject(1);
        Object outValue = statement.getObject(2);
        value[0] = outValue == null ? null : new Date( ((java.sql.Timestamp) outValue).getTime() );

        return (BigDecimal) data;
    }

}
