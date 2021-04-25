package ericminio.javaoracle.demos;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import ericminio.javaoracle.support.DatabaseTest;

import java.sql.SQLException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ExplorationTest extends DatabaseTest {

    @Before
    public void seeds() throws SQLException {
        execute("truncate table event");
        execute("insert into event(type, label) values('A', '1')");
        execute("insert into event(type, label) values('A', '2')");
        execute("insert into event(type, label) values('A', '3')");
    }

    @Test
    public void works() throws SQLException {
        Exploration exploration = new Exploration();
        exploration.setConnection(connection);
        
        assertThat(exploration.getEventCount(), equalTo(3));
    }
}