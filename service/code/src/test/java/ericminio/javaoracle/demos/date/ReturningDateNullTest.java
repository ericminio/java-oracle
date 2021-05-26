package ericminio.javaoracle.demos.date;

import ericminio.javaoracle.data.DatabaseTest;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.text.ParseException;

import static ericminio.javaoracle.data.Query.with;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class ReturningDateNullTest extends DatabaseTest {

    @Before
    public void seeds() {
        with(connection).execute("CREATE OR REPLACE PACKAGE returning_date_null\n" +
                "AS\n" +
                "\n" +
                "    FUNCTION get_value RETURN date;\n" +
                "\n" +
                "END returning_date_null;");
        with(connection).execute("create or replace package body returning_date_null\n" +
                "AS\n" +
                "\n" +
                "    function get_value return date\n" +
                "    as\n" +
                "    begin\n" +
                "        return null;\n" +
                "    end;\n" +
                "\n" +
                "END returning_date_null;");
    }

    @Test
    public void works() throws SQLException, ParseException {
        ReturningDateNull returningDateNull = new ReturningDateNull(connection);

        assertThat(returningDateNull.getValue(), equalTo(null));
    }
}