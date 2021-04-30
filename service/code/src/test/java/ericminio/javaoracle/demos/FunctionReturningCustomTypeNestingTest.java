package ericminio.javaoracle.demos;

import ericminio.javaoracle.support.DatabaseTest;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;

import static ericminio.javaoracle.data.Query.with;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class FunctionReturningCustomTypeNestingTest extends DatabaseTest {

    @Before
    public void createStructure() {
        with(connection).executeIgnoringFailure("drop type custom_type_nesting");
        with(connection).executeIgnoringFailure("drop type custom_type_nested");
        with(connection).execute("" +
                "create type custom_type_nested as object" +
                "(" +
                "   value number\n" +
                ");");
        with(connection).execute("" +
                "create type custom_type_nesting as object" +
                "(" +
                "   field custom_type_nested\n" +
                ");");
        with(connection).execute("" +
                "create or replace package returning_custom_type_nesting\n" +
                "AS\n" +
                "    FUNCTION get_field RETURN custom_type_nesting;\n" +
                "\n" +
                "END returning_custom_type_nesting;");
        with(connection).execute("" +
                "create or replace package body returning_custom_type_nesting\n" +
                "AS\n" +
                "\n" +
                "    function get_field return custom_type_nesting\n" +
                "    as\n" +
                "    begin\n" +
                "        return custom_type_nesting(custom_type_nested(15));\n" +
                "    end;\n" +
                "\n" +
                "END returning_custom_type_nesting;");
    }

    @Test
    public void customTypeCanBeFetched() throws SQLException {
        FunctionReturningCustomTypeNesting functionReturningCustomTypeNesting = new FunctionReturningCustomTypeNesting(connection);
        CustomTypeNested customTypeNested = new CustomTypeNested();
        customTypeNested.setValue(15);
        CustomTypeNesting customTypeNesting = new CustomTypeNesting();
        customTypeNesting.setValue(customTypeNested);

        assertThat(functionReturningCustomTypeNesting.getField(), equalTo(customTypeNesting));
    }
}
