package ericminio.javaoracle.demos;

import ericminio.javaoracle.support.DatabaseTest;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import static ericminio.javaoracle.data.Query.with;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class FunctionReturningCustomTypeTest extends DatabaseTest {

    @Before
    public void createStructure() {
        with(connection).execute("" +
                "create or replace type custom_type as object" +
                "(" +
                "   id number,\n" +
                "   label varchar2(15),\n" +
                "   creation_date date\n" +
                ");");
        with(connection).execute("" +
                "CREATE OR REPLACE PACKAGE function_returning_custom_type\n" +
                "AS\n" +
                "    FUNCTION get_value RETURN custom_type;\n" +
                "\n" +
                "END function_returning_custom_type;");
        with(connection).execute("" +
                "create or replace package body function_returning_custom_type\n" +
                "AS\n" +
                "\n" +
                "    function get_value return custom_type\n" +
                "    as\n" +
                "    begin\n" +
                "        return custom_type(15, 'hello', to_date('2015/01/15 19:15:42', 'YYYY/MM/DD HH24:MI:SS'));\n" +
                "    end;\n" +
                "\n" +
                "END function_returning_custom_type;");
    }

    @Test
    public void customTypeCanBeFetched() throws SQLException, ParseException {
        FunctionReturningCustomType functionReturningCustomType = new FunctionReturningCustomType(connection);
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy/M/dd hh:mm:ss");
        CustomType customType = new CustomType();
        customType.setId(new BigDecimal(15));
        customType.setLabel("hello");
        customType.setCreationDate(dateformat.parse("2015/01/15 19:15:42"));

        assertThat(functionReturningCustomType.getValue(), equalTo(customType));
    }
}
