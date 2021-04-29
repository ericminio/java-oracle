package ericminio.javaoracle.demos;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FunctionWithoutParameter {

    private Connection connection;

    public FunctionWithoutParameter(Connection connection) {
        this.connection = connection;
    }

    public BigDecimal getValue() throws SQLException {
        PreparedStatement statement = connection.prepareStatement("select function_without_parameter.get_value() from dual");
        ResultSet resultSet = statement.executeQuery();
        resultSet.next();

        return (BigDecimal) resultSet.getObject(1);
    }

}