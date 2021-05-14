package ericminio.javaoracle.demos;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TakingNoParameter {

    private Connection connection;

    public TakingNoParameter(Connection connection) {
        this.connection = connection;
    }

    public BigDecimal getValue() throws SQLException {
        PreparedStatement statement = connection.prepareStatement("select taking_no_parameter.get_value() from dual");
        ResultSet resultSet = statement.executeQuery();
        resultSet.next();
        Object data = resultSet.getObject(1);

        return (BigDecimal) data;
    }

}