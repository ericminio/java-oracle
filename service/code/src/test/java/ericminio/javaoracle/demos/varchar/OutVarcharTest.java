package ericminio.javaoracle.demos.varchar;

import ericminio.javaoracle.data.DatabaseTest;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.sql.SQLException;

import static ericminio.javaoracle.data.Query.with;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class OutVarcharTest extends DatabaseTest {

    @Before
    public void seeds() {
        with(connection).execute("CREATE OR REPLACE PACKAGE out_varchar\n" +
                "AS\n" +
                "\n" +
                "    function get_value(input in number, value out varchar) return number;\n" +
                "\n" +
                "END out_varchar;");
        with(connection).execute("create or replace package body out_varchar\n" +
                "AS\n" +
                "\n" +
                "    function get_value(input in number, value out varchar) return number\n" +
                "    as\n" +
                "    begin\n" +
                "        value := '42';\n" +
                "        return input+1;\n" +
                "    end;\n" +
                "\n" +
                "END out_varchar;");
    }

    @Test
    public void works() throws SQLException {
        OutVarchar outVarchar = new OutVarchar(connection);
        String[] value = new String[1];
        BigDecimal status = outVarchar.getValue(new BigDecimal(41), value);

        assertThat(status, equalTo(new BigDecimal(42)));
        assertThat(value[0], equalTo("42"));
    }
}