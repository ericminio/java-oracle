package ericminio.javaoracle.support;

import oracle.jdbc.pool.OracleDataSource;
import org.junit.After;
import org.junit.Before;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseTest {

    private Environment environment;
    protected Connection connection;

    public DataSource getDataSource() throws Exception {
        OracleDataSource dataSource = new OracleDataSource();
        dataSource.setURL("jdbc:oracle:thin:@"+ environment.getOracleHost() + ":1521:XE");
        dataSource.setUser("system");
        dataSource.setPassword("oracle");

        return dataSource;
    }

    @Before
    public void newConnection() throws Exception {
        environment = new Environment();
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