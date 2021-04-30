package ericminio.javaoracle.demos;

import ericminio.javaoracle.support.DatabaseTest;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.sql.SQLException;

import static ericminio.javaoracle.data.Query.with;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class FunctionReturningNumberTest extends DatabaseTest {

    @Before
    public void seeds() {
        with(connection).execute("CREATE OR REPLACE PACKAGE function_returning_number\n" +
                "AS\n" +
                "\n" +
                "    FUNCTION get_value RETURN number;\n" +
                "\n" +
                "END function_returning_number;");
        with(connection).execute("create or replace package body function_returning_number\n" +
                "AS\n" +
                "\n" +
                "    function get_value return number\n" +
                "    as\n" +
                "    begin\n" +
                "        return 42;\n" +
                "    end;\n" +
                "\n" +
                "END function_returning_number;");
    }

    @Test
    public void works() throws SQLException {
        FunctionReturningNumber functionReturningNumber = new FunctionReturningNumber(connection);
        
        assertThat(functionReturningNumber.getValue(), equalTo(new BigDecimal(42)));
    }
}