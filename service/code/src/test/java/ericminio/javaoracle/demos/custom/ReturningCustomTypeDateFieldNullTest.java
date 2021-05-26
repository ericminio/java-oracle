package ericminio.javaoracle.demos.custom;

import ericminio.javaoracle.data.DatabaseTest;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

import static ericminio.javaoracle.data.Query.with;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class ReturningCustomTypeDateFieldNullTest extends DatabaseTest {

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
                "CREATE OR REPLACE PACKAGE custom_type_date_null\n" +
                "AS\n" +
                "    FUNCTION get_value RETURN custom_type;\n" +
                "\n" +
                "END custom_type_date_null;");
        with(connection).execute("" +
                "create or replace package body custom_type_date_null\n" +
                "AS\n" +
                "\n" +
                "    function get_value return custom_type\n" +
                "    as\n" +
                "    begin\n" +
                "        return custom_type(15, 'hello', null);\n" +
                "    end;\n" +
                "\n" +
                "END custom_type_date_null;");
    }

    @Test
    public void customTypeCanBeFetched() throws SQLException {
        ReturningCustomTypeDateFieldNull returningCustomTypeDateFieldNull = new ReturningCustomTypeDateFieldNull(connection);
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy/M/dd hh:mm:ss");
        CustomType customType = new CustomType();
        customType.setId(new BigDecimal(15));
        customType.setLabel("hello");
        customType.setCreationDate(null);

        assertThat(returningCustomTypeDateFieldNull.getValue(), equalTo(customType));
    }
}
