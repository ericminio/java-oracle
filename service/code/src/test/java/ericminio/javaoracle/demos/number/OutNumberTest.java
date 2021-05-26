package ericminio.javaoracle.demos.number;

import ericminio.javaoracle.data.DatabaseTest;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.sql.SQLException;

import static ericminio.javaoracle.data.Query.with;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class OutNumberTest extends DatabaseTest {

    @Before
    public void seeds() {
        with(connection).execute("CREATE OR REPLACE PACKAGE out_number\n" +
                "AS\n" +
                "\n" +
                "    function get_value(input in number, value out number) return number;\n" +
                "\n" +
                "END out_number;");
        with(connection).execute("create or replace package body out_number\n" +
                "AS\n" +
                "\n" +
                "    function get_value(input in number, value out number) return number\n" +
                "    as\n" +
                "    begin\n" +
                "        value := input+1;\n" +
                "        return input+1;\n" +
                "    end;\n" +
                "\n" +
                "END out_number;");
    }

    @Test
    public void works() throws SQLException {
        OutNumber outNumber = new OutNumber(connection);
        BigDecimal[] value = new BigDecimal[1];
        BigDecimal status = outNumber.getValue(new BigDecimal(41), value);

        assertThat(status, equalTo(new BigDecimal(42)));
        assertThat(value[0], equalTo(new BigDecimal(42)));
    }
}