package ericminio.javaoracle.demos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ReturningCursor {

    private Connection connection;

    public ReturningCursor(Connection connection) {
        this.connection = connection;
    }

    public ResultSet getProducts() throws SQLException {
        PreparedStatement statement = connection.prepareStatement("select returning_cursor.get_products() from dual");
        ResultSet resultSet = statement.executeQuery();
        resultSet.next();
        Object data = resultSet.getObject(1);

        return (ResultSet) data;
    }

}
