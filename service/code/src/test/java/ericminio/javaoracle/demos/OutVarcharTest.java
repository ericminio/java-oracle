package ericminio.javaoracle.demos;

import ericminio.javaoracle.data.DatabaseTest;
import org.junit.Before;
import org.junit.Test;

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
                "    PROCEDURE get_value(value out varchar);\n" +
                "\n" +
                "END out_varchar;");
        with(connection).execute("create or replace package body out_varchar\n" +
                "AS\n" +
                "\n" +
                "    PROCEDURE get_value(value out varchar)\n" +
                "    as\n" +
                "    begin\n" +
                "        value := '42';\n" +
                "    end;\n" +
                "\n" +
                "END out_varchar;");
    }

    @Test
    public void works() throws SQLException {
        OutVarchar outVarchar = new OutVarchar(connection);

        assertThat(outVarchar.getValue(), equalTo("42"));
    }
}