package ericminio.javaoracle.data;

import ericminio.javaoracle.support.Stringify;
import org.junit.After;
import org.junit.Before;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;

import static ericminio.javaoracle.data.Query.with;

public class DatabaseTest {

    protected Connection connection;
    protected SimpleDateFormat dateformat;

    @Before
    public void newConnection() throws Exception {
        connection = new Database().connection();
        dropPackages();
        Integer count = 0;
        do {
            dropTypes();
            count = with(connection).selectInt("select count(1) from user_types");
        } while (count > 0);
        dateformat = new SimpleDateFormat("yyyy/M/dd hh:mm:ss");
    }

    @After
    public void closeConnection() throws SQLException {
        connection.close();
    }

    public void executeFromResource(String name) throws IOException {
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(name);
        String sql = new Stringify().inputStream(inputStream);
        String[] statements = sql.split("/");
        for (int i=0; i<statements.length; i++) {
            String statement = statements[i].trim();
            if (statement.length() > 0) {
                with(connection).execute(statement);
            }
        }
    }

    private void dropPackages() {
        List<String> names = with(connection).selectStrings("select object_name from user_objects where object_type='PACKAGE'");
        for (String name:names) {
            with(connection).executeIgnoringFailure("drop package body " + name);
            with(connection).executeIgnoringFailure("drop package " + name);
        }
    }

    private void dropTypes() {
        List<String> names = with(connection).selectStrings("select type_name from user_types");
        for (String name:names) {
            with(connection).executeIgnoringFailure("drop type " + name);
        }
    }
}