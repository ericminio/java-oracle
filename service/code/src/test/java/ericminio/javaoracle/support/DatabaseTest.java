package ericminio.javaoracle.support;

import org.junit.After;
import org.junit.Before;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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

    protected void execute(String sql) {
        Statement statement = null; 
        try {            
            statement = connection.createStatement();
            statement.executeUpdate(sql);
        }
        catch (SQLException executing) {
            throw new RuntimeException(executing.getMessage() + " " + sql);
        }
        finally {
            try {
                statement.close();
            }
            catch (SQLException closing) {
                throw new RuntimeException(closing.getMessage());
            }
        }
    }

    protected int selectInt(String sql) {
        Statement statement = null; 
        try {            
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            resultSet.next();
            return resultSet.getInt(1);
        }
        catch (SQLException executing) {
            throw new RuntimeException(executing.getMessage() + " " + sql);
        }
        finally {
            try {
                statement.close();
            }
            catch (SQLException closing) {
                throw new RuntimeException(closing.getMessage());
            }
        }
    }
}