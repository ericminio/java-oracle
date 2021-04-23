package ericminio;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;

import javax.sql.DataSource;

import oracle.jdbc.pool.OracleDataSource;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class GetEventCountTest {

    Connection connection;

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

    @Test
    public void worksWithRawSql() throws Exception {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select get_event_count() as count from dual");
        resultSet.next();
        int count = resultSet.getInt("count");

        assertThat(count, equalTo(5));
    }

    @Test
    public void worksWithCallableStatement() throws Exception {
        CallableStatement statement = connection.prepareCall("{? = call get_event_count()}");
        statement.registerOutParameter(1, Types.INTEGER);
        statement.executeUpdate();
        int count = statement.getInt(1);
        
        assertThat(count, equalTo(5));
    }
}
