package ericminio.javaoracle.demos;

import ericminio.javaoracle.data.DatabaseTest;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.sql.SQLException;

import static ericminio.javaoracle.data.Query.with;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class TakingNoParameterTest extends DatabaseTest {

    @Before
    public void seeds() {
        with(connection).execute("CREATE OR REPLACE PACKAGE taking_no_parameter\n" +
                "AS\n" +
                "\n" +
                "    FUNCTION get_value RETURN number;\n" +
                "\n" +
                "END taking_no_parameter;");
        with(connection).execute("create or replace package body taking_no_parameter\n" +
                "AS\n" +
                "\n" +
                "    function get_value return number\n" +
                "    as\n" +
                "    begin\n" +
                "        return 42;\n" +
                "    end;\n" +
                "\n" +
                "END taking_no_parameter;");
    }

    @Test
    public void works() throws SQLException {
        TakingNoParameter takingNoParameter = new TakingNoParameter(connection);
        
        assertThat(takingNoParameter.getValue(), equalTo(new BigDecimal(42)));
    }
}