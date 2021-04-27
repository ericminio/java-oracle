package ericminio.javaoracle.demos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FunctionWithParameter {

    private Connection connection;

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public Integer countByType(String value) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("select function_with_parameter.count_by_type(?) from dual");
        statement.setString(1, value);
        ResultSet resultSet = statement.executeQuery();
        resultSet.next();

        return resultSet.getInt(1);
    }

    public Integer countByLabel(String value) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("select function_with_parameter.count_by_label(?) from dual");
        statement.setString(1, value);
        ResultSet resultSet = statement.executeQuery();
        resultSet.next();

        return resultSet.getInt(1);
    }

}