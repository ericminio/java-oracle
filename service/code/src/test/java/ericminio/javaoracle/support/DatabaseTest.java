package ericminio.javaoracle.support;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import javax.sql.DataSource;
import oracle.jdbc.pool.OracleDataSource;

import org.junit.After;
import org.junit.Before;

public class DatabaseTest {

    protected Connection connection;

    public DataSource getDataSource() throws SQLException {
        OracleDataSource dataSource = new OracleDataSource();
        dataSource.setURL("jdbc:oracle:thin:@oracle:1521:XE");
        dataSource.setUser("system");
        dataSource.setPassword("oracle");

        return dataSource;
    }

    @Before
    public void newConnection() throws SQLException {
        connection = getDataSource().getConnection();
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