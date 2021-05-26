package ericminio.javaoracle.demos.date;

import ericminio.javaoracle.data.DatabaseTest;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;

import static ericminio.javaoracle.data.Query.with;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class OutDateTest extends DatabaseTest {

    @Before
    public void seeds() {
        with(connection).execute("CREATE OR REPLACE PACKAGE out_date\n" +
                "AS\n" +
                "\n" +
                "    function get_value(value out date) return number;\n" +
                "\n" +
                "END out_date;");
        with(connection).execute("create or replace package body out_date\n" +
                "AS\n" +
                "\n" +
                "    function get_value(value out date) return number\n" +
                "    as\n" +
                "    begin\n" +
                "        value := to_date('2015/01/15 19:15:42', 'YYYY/MM/DD HH24:MI:SS');\n" +
                "        return 1;\n" +
                "    end;\n" +
                "\n" +
                "END out_date;");
    }

    @Test
    public void works() throws SQLException, ParseException {
        OutDate outDate = new OutDate(connection);
        Date expected = dateformat.parse("2015/01/15 19:15:42");
        Date[] value = new Date[1];
        outDate.getValue(value);

        assertThat(value[0], equalTo(expected));
    }
}