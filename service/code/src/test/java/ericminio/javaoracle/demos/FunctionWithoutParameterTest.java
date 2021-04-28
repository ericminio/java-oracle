package ericminio.javaoracle.demos;

import ericminio.javaoracle.support.DatabaseTest;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;

import static ericminio.javaoracle.data.Query.with;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class FunctionWithoutParameterTest extends DatabaseTest {

    @Before
    public void seeds() {
        with(connection).execute("CREATE OR REPLACE PACKAGE function_without_parameter\n" +
                "AS\n" +
                "\n" +
                "    FUNCTION get_value RETURN integer;\n" +
                "\n" +
                "END function_without_parameter;");
        with(connection).execute("create or replace package body function_without_parameter\n" +
                "AS\n" +
                "\n" +
                "    function get_value return integer\n" +
                "    as\n" +
                "    begin\n" +
                "        return 42;\n" +
                "    end;\n" +
                "\n" +
                "END function_without_parameter;");
    }

    @Test
    public void works() throws SQLException {
        FunctionWithoutParameter functionWithoutParameter = new FunctionWithoutParameter(connection);
        
        assertThat(functionWithoutParameter.getValue(), equalTo(42));
    }
}