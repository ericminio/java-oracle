package ericminio.javaoracle.demos;

import ericminio.javaoracle.data.DatabaseTest;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;

import static ericminio.javaoracle.data.Query.with;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ReturningArrayOfVarcharTest extends DatabaseTest {

    @Before
    public void createStructure() {
        with(connection).execute("create or replace type array_of_varchar as varray(5) of varchar2(15);");
        with(connection).execute("" +
                "create or replace package returning_array_of_varchar\n" +
                "AS\n" +
                "    FUNCTION get_value RETURN array_of_varchar;\n" +
                "END returning_array_of_varchar;");
        with(connection).execute("" +
                "create or replace package body returning_array_of_varchar\n" +
                "IS\n" +
                "    function get_value return array_of_varchar\n" +
                "    as\n" +
                "        R array_of_varchar;\n" +
                "    begin\n" +
                "        R := array_of_varchar();\n" +
                "        R.extend;\n" +
                "        R(1) := 'hello';\n" +
                "        R.extend;\n" +
                "        R(2) := 'world';\n" +
                "        return R;\n" +
                "    end;\n" +
                "END returning_array_of_varchar;");
    }

    @Test
    public void returnsExpectedArraySize() throws SQLException {
        ReturningArrayOfVarchar returningArrayOfVarchar = new ReturningArrayOfVarchar(connection);
        ArrayOfVarchar actual = returningArrayOfVarchar.getValue();

        assertThat(actual.length(), equalTo(2));
    }

    @Test
    public void returnsExpectedArrayContent() throws SQLException {
        ReturningArrayOfVarchar returningArrayOfVarchar = new ReturningArrayOfVarchar(connection);
        ArrayOfVarchar actual = returningArrayOfVarchar.getValue();
        String[] entries = actual.getArray();

        assertThat(entries.length, equalTo(2));
    }

    @Test
    public void exposesElements() throws SQLException {
        ReturningArrayOfVarchar returningArrayOfVarchar = new ReturningArrayOfVarchar(connection);
        ArrayOfVarchar actual = returningArrayOfVarchar.getValue();

        assertThat(actual.getElement(0), equalTo("hello"));
        assertThat(actual.getElement(1), equalTo("world"));
    }
}
