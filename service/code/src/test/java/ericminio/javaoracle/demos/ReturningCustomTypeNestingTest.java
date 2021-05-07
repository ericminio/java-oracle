package ericminio.javaoracle.demos;

import ericminio.javaoracle.data.DatabaseTest;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.ParseException;

import static ericminio.javaoracle.data.Query.with;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class ReturningCustomTypeNestingTest extends DatabaseTest {

    @Before
    public void createStructure() {
        with(connection).execute("create type custom_type as object (id number, label varchar2(15), creation_date date);");
        with(connection).execute("create type custom_type_nesting as object (nested custom_type);");
        with(connection).execute("" +
                "CREATE OR REPLACE PACKAGE returning_custom_type_nesting\n" +
                "AS\n" +
                "    FUNCTION get_value RETURN custom_type_nesting;\n" +
                "\n" +
                "END returning_custom_type_nesting;");
        with(connection).execute("" +
                "create or replace package body returning_custom_type_nesting\n" +
                "AS\n" +
                "\n" +
                "    function get_value return custom_type_nesting\n" +
                "    as\n" +
                "    begin\n" +
                "        return custom_type_nesting(custom_type(15, 'hello', to_date('2015/01/15 19:15:42', 'YYYY/MM/DD HH24:MI:SS')));\n" +
                "    end;\n" +
                "\n" +
                "END returning_custom_type_nesting;");
    }

    @Test
    public void customTypeNestingCanBeFetched() throws SQLException, ParseException {
        ReturningCustomTypeNesting returningCustomTypeNesting = new ReturningCustomTypeNesting(connection);
        CustomType customType = new CustomType();
        customType.setId(new BigDecimal(15));
        customType.setLabel("hello");
        customType.setCreationDate(dateformat.parse("2015/01/15 19:15:42"));
        CustomTypeNesting expected = new CustomTypeNesting();
        expected.setNested(customType);

        assertThat(returningCustomTypeNesting.getValue(), equalTo(expected));
    }
}
