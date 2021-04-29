package ericminio.javaoracle.demos;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FunctionWithParameter {

    private Connection connection;

    public FunctionWithParameter(Connection connection) {
        this.connection = connection;
    }

    public BigDecimal countByType(String value) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("select function_with_parameter.count_by_type(?) from dual");
        statement.setString(1, value);
        ResultSet resultSet = statement.executeQuery();
        resultSet.next();

        return (BigDecimal) resultSet.getObject(1);
    }

    public BigDecimal countByLabel(String value) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("select function_with_parameter.count_by_label(?) from dual");
        statement.setString(1, value);
        ResultSet resultSet = statement.executeQuery();
        resultSet.next();

        return (BigDecimal) resultSet.getObject(1);
    }

}