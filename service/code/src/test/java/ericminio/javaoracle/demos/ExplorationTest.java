package ericminio.javaoracle.demos;

import ericminio.javaoracle.support.DatabaseTest;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;

import static ericminio.javaoracle.support.Query.with;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class ExplorationTest extends DatabaseTest {

    @Before
    public void seeds() throws SQLException {
        with(connection).execute("truncate table event");
        with(connection).execute("insert into event(type, label) values('A', '1')");
        with(connection).execute("insert into event(type, label) values('A', '2')");
        with(connection).execute("insert into event(type, label) values('A', '3')");
    }

    @Test
    public void works() throws SQLException {
        Exploration exploration = new Exploration();
        exploration.setConnection(connection);
        
        assertThat(exploration.getEventCount(), equalTo(3));
    }
}