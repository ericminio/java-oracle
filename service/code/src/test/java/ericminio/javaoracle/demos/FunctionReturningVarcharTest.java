package ericminio.javaoracle.demos;

import ericminio.javaoracle.support.DatabaseTest;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;

import static ericminio.javaoracle.data.Query.with;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class FunctionReturningVarcharTest extends DatabaseTest {

    @Before
    public void seeds() {
        with(connection).execute("CREATE OR REPLACE PACKAGE function_returning_varchar\n" +
                "AS\n" +
                "\n" +
                "    FUNCTION get_value RETURN varchar2;\n" +
                "\n" +
                "END function_returning_varchar;");
        with(connection).execute("create or replace package body function_returning_varchar\n" +
                "AS\n" +
                "\n" +
                "    function get_value return varchar2\n" +
                "    as\n" +
                "    begin\n" +
                "        return '42';\n" +
                "    end;\n" +
                "\n" +
                "END function_returning_varchar;");
    }

    @Test
    public void works() throws SQLException {
        FunctionReturningVarchar functionReturningVarchar = new FunctionReturningVarchar(connection);
        
        assertThat(functionReturningVarchar.getValue(), equalTo("42"));
    }
}