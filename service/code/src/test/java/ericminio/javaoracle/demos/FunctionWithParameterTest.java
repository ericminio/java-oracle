package ericminio.javaoracle.demos;

import ericminio.javaoracle.support.DatabaseTest;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class FunctionWithParameterTest extends DatabaseTest {

    @Before
    public void seeds() throws SQLException {
        execute("truncate table event");
        execute("insert into event(type, label) values('A', '42')");
        execute("insert into event(type, label) values('A', '42')");
        execute("insert into event(type, label) values('B', '42')");
    }

    @Test
    public void findByType() throws SQLException {
        FunctionWithParameter functionWithParameter = new FunctionWithParameter();
        functionWithParameter.setConnection(connection);
        
        assertThat(functionWithParameter.countByType("A"), equalTo(2));
        assertThat(functionWithParameter.countByType("B"), equalTo(1));
    }

    @Test
    public void findByLabel() throws SQLException {
        FunctionWithParameter functionWithParameter = new FunctionWithParameter();
        functionWithParameter.setConnection(connection);

        assertThat(functionWithParameter.countByLabel("42"), equalTo(3));
        assertThat(functionWithParameter.countByLabel("99"), equalTo(0));
    }
}