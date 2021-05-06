package ericminio.javaoracle.demos;

import ericminio.javaoracle.data.DatabaseTest;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static ericminio.javaoracle.data.Query.with;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class ReturningDateTest extends DatabaseTest {

    @Before
    public void seeds() {
        with(connection).execute("CREATE OR REPLACE PACKAGE returning_date\n" +
                "AS\n" +
                "\n" +
                "    FUNCTION get_value RETURN date;\n" +
                "\n" +
                "END returning_date;");
        with(connection).execute("create or replace package body returning_date\n" +
                "AS\n" +
                "\n" +
                "    function get_value return date\n" +
                "    as\n" +
                "    begin\n" +
                "        return to_date('2015/01/15 19:15:42', 'YYYY/MM/DD HH24:MI:SS');\n" +
                "    end;\n" +
                "\n" +
                "END returning_date;");
    }

    @Test
    public void works() throws SQLException, ParseException {
        ReturningDate returningDate = new ReturningDate(connection);
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy/M/dd hh:mm:ss");
        Date expected = dateformat.parse("2015/01/15 19:15:42");

        assertThat(returningDate.getValue(), equalTo(expected));
    }
}