package ericminio.javaoracle.demos;

import ericminio.javaoracle.data.DatabaseTest;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;

import static ericminio.javaoracle.data.Query.with;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ReturningArrayEmptyTest extends DatabaseTest {

    @Before
    public void createStructure() {
        with(connection).execute("create or replace type custom_type as object(id number, label varchar2(15), creation_date date);");
        with(connection).execute("create or replace type array_of_custom_type as varray(15) of custom_type;");
        with(connection).execute("" +
                "create or replace package returning_array_empty\n" +
                "AS\n" +
                "    FUNCTION get_value RETURN array_of_custom_type;\n" +
                "END returning_array_empty;");
        with(connection).execute("" +
                "create or replace package body returning_array_empty\n" +
                "IS\n" +
                "    function get_value return array_of_custom_type\n" +
                "    as\n" +
                "        R array_of_custom_type;\n" +
                "    begin\n" +
                "        R := array_of_custom_type();\n" +
                "        return R;\n" +
                "    end;\n" +
                "END returning_array_empty;");
    }

    @Test
    public void returnsExpectedArraySize() throws SQLException {
        ReturningArrayEmpty returningArrayEmpty = new ReturningArrayEmpty(connection);
        ArrayOfCustomType actual = returningArrayEmpty.getValue();

        assertThat(actual.length(), equalTo(0));
    }

    @Test
    public void returnsExpectedArrayContent() throws SQLException {
        ReturningArrayEmpty returningArrayEmpty = new ReturningArrayEmpty(connection);
        ArrayOfCustomType actual = returningArrayEmpty.getValue();
        CustomType[] entries = actual.getArray();

        assertThat(entries.length, equalTo(0));
    }
}
