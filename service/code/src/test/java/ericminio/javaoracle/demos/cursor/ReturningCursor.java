package ericminio.javaoracle.demos.cursor;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ReturningCursor {

    private Connection connection;

    public ReturningCursor(Connection connection) {
        this.connection = connection;
    }

    public ResultSet getProducts() throws SQLException {
        CallableStatement statement = connection.prepareCall("{ ? = call returning_cursor.get_products() }");
        statement.registerOutParameter(1, oracle.jdbc.OracleTypes.CURSOR);
        statement.executeUpdate();
        Object data = statement.getObject(1);

        return (ResultSet) data;
    }

}
