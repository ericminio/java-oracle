package ericminio.javaoracle.support;

import ericminio.javaoracle.data.Database;
import org.junit.After;
import org.junit.Before;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

public class DatabaseTest {

    protected Connection connection;
    protected SimpleDateFormat dateformat;

    @Before
    public void newConnection() throws Exception {
        connection = new Database().connection();
        dateformat = new SimpleDateFormat("yyyy/M/dd hh:mm:ss");
    }
    @After
    public void closeConnection() throws SQLException {
        connection.close();
    }
}