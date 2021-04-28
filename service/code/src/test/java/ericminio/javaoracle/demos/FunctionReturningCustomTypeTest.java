package ericminio.javaoracle.demos;

import ericminio.javaoracle.support.DatabaseTest;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;

import static ericminio.javaoracle.data.Query.with;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class FunctionReturningCustomTypeTest extends DatabaseTest {

    @Before
    public void createStructure() {
        with(connection).execute("" +
                "create or replace type custom_type as object" +
                "(" +
                "   value integer\n" +
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
                "        return custom_type(15);\n" +
                "    end;\n" +
                "\n" +
                "END function_returning_custom_type;");
    }

    @Test
    public void customTypeCanBeFetched() throws SQLException {
        FunctionReturningCustomType functionReturningCustomType = new FunctionReturningCustomType(connection);
        CustomType customType = new CustomType();
        customType.setValue(15);

        assertThat(functionReturningCustomType.getValue(), equalTo(customType));
    }
}
