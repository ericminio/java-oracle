package ericminio.javaoracle.demos;

import ericminio.javaoracle.support.DatabaseTest;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static ericminio.javaoracle.data.Query.with;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class FunctionTakingDateTest extends DatabaseTest {

    @Before
    public void seeds() {
        with(connection).execute("CREATE OR REPLACE PACKAGE function_taking_date\n" +
                "AS\n" +
                "\n" +
                "    FUNCTION get_value(\n" +
                "        input date\n" +
                "    ) RETURN date;\n" +
                "\n" +
                "END function_taking_date;");
        with(connection).execute("create or replace package body function_taking_date\n" +
                "AS\n" +
                "\n" +
                "    function get_value(\n" +
                "        input date\n" +
                "    ) return date\n" +
                "    as\n" +
                "    begin\n" +
                "        return input + 1;\n" +
                "    end;\n" +
                "\n" +
                "END function_taking_date;");
    }

    @Test
    public void getValue() throws SQLException, ParseException {
        FunctionTakingDate functionTakingDate = new FunctionTakingDate(connection);
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy/M/dd hh:mm:ss");
        Date input = dateformat.parse("2015/01/14 19:15:42");
        Date expected = dateformat.parse("2015/01/15 19:15:42");

        assertThat(functionTakingDate.getValue(input), equalTo(expected));
    }
}