package ericminio.javaoracle.support;

import ericminio.javaoracle.data.Database;
import org.junit.After;
import org.junit.Before;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

import static ericminio.javaoracle.data.Query.with;

public class DatabaseTest {

    protected Connection connection;
    protected SimpleDateFormat dateformat;

    @Before
    public void newConnection() throws Exception {
        connection = new Database().connection();
        dateformat = new SimpleDateFormat("yyyy/M/dd hh:mm:ss");
        with(connection).executeIgnoringFailure("drop package body returning_complex_type");
        with(connection).executeIgnoringFailure("drop package returning_complex_type");
        with(connection).executeIgnoringFailure("drop package body returning_array_of__custom_type");
        with(connection).executeIgnoringFailure("drop package returning_array_of_custom_type");
        with(connection).executeIgnoringFailure("drop package body returning_custom_type");
        with(connection).executeIgnoringFailure("drop package returning_custom_type");
        with(connection).executeIgnoringFailure("drop type complex_type");
        with(connection).executeIgnoringFailure("drop type array_of_custom_type");
        with(connection).executeIgnoringFailure("drop type custom_type");
    }
    @After
    public void closeConnection() throws SQLException {
        connection.close();
    }
}