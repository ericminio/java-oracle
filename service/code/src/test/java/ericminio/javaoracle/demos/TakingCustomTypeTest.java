package ericminio.javaoracle.demos;

import ericminio.javaoracle.data.DatabaseTest;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import static ericminio.javaoracle.data.Query.with;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class TakingCustomTypeTest extends DatabaseTest {

    @Before
    public void seeds() {
        with(connection).execute("" +
                "create or replace type custom_type as object (\n" +
                "   id number,\n" +
                "   label varchar2(15),\n" +
                "   creation_date date\n" +
                ");");

        with(connection).execute("" +
                "CREATE OR REPLACE PACKAGE taking_custom_type\n" +
                "AS\n" +
                "    FUNCTION get_value(input custom_type) RETURN custom_type;\n" +
                "\n" +
                "END taking_custom_type;");
        with(connection).execute("" +
                "create or replace package body taking_custom_type\n" +
                "AS\n" +
                "\n" +
                "    function get_value(input custom_type) return custom_type\n" +
                "    as\n" +
                "    begin\n" +
                "        return custom_type(input.id + 1, 'Hello ' || input.label, input.creation_date + 1);\n" +
                "    end;\n" +
                "\n" +
                "END taking_custom_type;");
    }

    @Test
    public void getValue() throws SQLException, ParseException {
        TakingCustomType takingCustomType = new TakingCustomType(connection);
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy/M/dd hh:mm:ss");
        CustomType input = new CustomType();
        input.setId(new BigDecimal(15));
        input.setLabel("Bob");
        input.setCreationDate(dateformat.parse("2015/01/14 19:15:42"));

        CustomType expected = new CustomType();
        expected.setId(new BigDecimal(16));
        expected.setLabel("Hello Bob");
        expected.setCreationDate(dateformat.parse("2015/01/15 19:15:42"));

        assertThat(takingCustomType.getValue(input), equalTo(expected));
    }
}