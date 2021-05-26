package ericminio.javaoracle.demos.number;

import ericminio.javaoracle.data.DatabaseTest;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.sql.SQLException;

import static ericminio.javaoracle.data.Query.with;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class TakingNumberTest extends DatabaseTest {

    @Before
    public void seeds() {
        with(connection).execute("CREATE OR REPLACE PACKAGE taking_number\n" +
                "AS\n" +
                "\n" +
                "    FUNCTION get_value(\n" +
                "        input number\n" +
                "    ) RETURN number;\n" +
                "\n" +
                "END taking_number;");
        with(connection).execute("create or replace package body taking_number\n" +
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
                "END taking_number;");
    }

    @Test
    public void worksAsExpected() throws SQLException {
        TakingNumber takingNumber = new TakingNumber(connection);

        assertThat(takingNumber.getValue(new BigDecimal(41)), equalTo(new BigDecimal(42)));
    }

    @Test
    public void worksWithDecimal() throws SQLException {
        TakingNumber takingNumber = new TakingNumber(connection);

        assertThat(takingNumber.getValue(new BigDecimal(41.2)).floatValue(), equalTo(42.2f));
    }
}