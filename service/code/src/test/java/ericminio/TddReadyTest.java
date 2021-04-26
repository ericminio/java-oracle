package ericminio;

import ericminio.javaoracle.support.DatabaseTest;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class TddReadyTest extends DatabaseTest {

    @Before
    public void seeds() throws SQLException {
        execute("truncate table event");
        execute("insert into event(type, label) values('A', '1')");
        execute("insert into event(type, label) values('A', '2')");
    }

    @Test
    public void canSelect() throws Exception {
        int count = selectInt("select exploration.get_event_count() as count from dual");

        assertThat(count, equalTo(2));
    }
}