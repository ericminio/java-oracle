package ericminio.javaoracle.support;

import org.junit.After;
import org.junit.Before;

import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseTest {

    protected Connection connection;

    @Before
    public void newConnection() throws Exception {
        connection = new Database().connection();
    }
    @After
    public void closeConnection() throws SQLException {
        connection.close();
    }
}