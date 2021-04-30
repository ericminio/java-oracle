package ericminio.javaoracle.demos;

import ericminio.javaoracle.support.DatabaseTest;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.sql.SQLException;

import static ericminio.javaoracle.data.Query.with;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class FunctionTakingCustomTypeTest extends DatabaseTest {

    @Before
    public void seeds() {
        with(connection).execute("" +
                "create or replace type custom_type as object" +
                "(" +
                "   value integer\n" +
                ");");

        with(connection).execute("" +
                "CREATE OR REPLACE PACKAGE function_taking_custom_type\n" +
                "AS\n" +
                "    FUNCTION get_value(input custom_type) RETURN custom_type;\n" +
                "\n" +
                "END function_taking_custom_type;");
        with(connection).execute("" +
                "create or replace package body function_taking_custom_type\n" +
                "AS\n" +
                "\n" +
                "    function get_value(input custom_type) return custom_type\n" +
                "    as\n" +
                "    begin\n" +
                "        return custom_type(input.value + 1);\n" +
                "    end;\n" +
                "\n" +
                "END function_taking_custom_type;");
    }

    @Test
    public void getValue() throws SQLException {
        FunctionTakingCustomType functionTakingCustomType = new FunctionTakingCustomType(connection);
        CustomType input = new CustomType();
        input.setValue(new BigDecimal(41));
        CustomType expected = new CustomType();
        expected.setValue(new BigDecimal(42));

        assertThat(functionTakingCustomType.getValue(input), equalTo(expected));
    }
}