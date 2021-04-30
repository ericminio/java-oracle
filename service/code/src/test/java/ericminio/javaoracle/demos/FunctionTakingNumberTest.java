package ericminio.javaoracle.demos;

import ericminio.javaoracle.support.DatabaseTest;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.sql.SQLException;

import static ericminio.javaoracle.data.Query.with;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class FunctionTakingNumberTest extends DatabaseTest {

    @Before
    public void seeds() {
        with(connection).execute("CREATE OR REPLACE PACKAGE function_taking_number\n" +
                "AS\n" +
                "\n" +
                "    FUNCTION get_value(\n" +
                "        input number\n" +
                "    ) RETURN number;\n" +
                "\n" +
                "END function_taking_number;");
        with(connection).execute("create or replace package body function_taking_number\n" +
                "AS\n" +
                "\n" +
                "    function get_value(\n" +
                "        input number\n" +
                "    ) return number\n" +
                "    as\n" +
                "    begin\n" +
                "        return input + 1;\n" +
                "    end;\n" +
                "\n" +
                "END function_taking_number;");
    }

    @Test
    public void getValue() throws SQLException {
        FunctionTakingNumber functionTakingNumber = new FunctionTakingNumber(connection);

        assertThat(functionTakingNumber.getValue(new BigDecimal(41)), equalTo(new BigDecimal(42)));
    }
}