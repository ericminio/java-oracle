package ericminio.javaoracle.demos;

import ericminio.javaoracle.data.DatabaseTest;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;

import static ericminio.javaoracle.data.Query.with;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ReturningArrayOfCustomTypeTest extends DatabaseTest {

    @Before
    public void createStructure() {
        with(connection).execute("create or replace type custom_type as object(id number, label varchar2(15), creation_date date);");
        with(connection).execute("create or replace type array_of_custom_type as varray(15) of custom_type;");
        with(connection).execute("" +
                "create or replace package returning_array_of_custom_type\n" +
                "AS\n" +
                "    FUNCTION get_value RETURN array_of_custom_type;\n" +
                "END returning_array_of_custom_type;");
        with(connection).execute("" +
                "create or replace package body returning_array_of_custom_type\n" +
                "IS\n" +
                "    function get_value return array_of_custom_type\n" +
                "    as\n" +
                "        R array_of_custom_type;\n" +
                "    begin\n" +
                "        R := array_of_custom_type();\n" +
                "        R.extend;\n" +
                "        R(1) := custom_type(15, 'hello', to_date('2015/01/15 19:15:42', 'YYYY/MM/DD HH24:MI:SS'));\n" +
                "        R.extend;\n" +
                "        R(2) := custom_type(42, 'world', to_date('2015/01/15 19:15:42', 'YYYY/MM/DD HH24:MI:SS'));\n" +
                "        return R;\n" +
                "    end;\n" +
                "END returning_array_of_custom_type;");
    }

    @Test
    public void returnsExpectedArraySize() throws SQLException {
        ReturningArrayOfCustomType returningArrayOfCustomType = new ReturningArrayOfCustomType(connection);
        ArrayOfCustomType actual = returningArrayOfCustomType.getValue();

        assertThat(actual.length(), equalTo(2));
    }

    @Test
    public void returnsExpectedArrayContent() throws SQLException {
        ReturningArrayOfCustomType returningArrayOfCustomType = new ReturningArrayOfCustomType(connection);
        ArrayOfCustomType actual = returningArrayOfCustomType.getValue();
        CustomType[] entries = actual.getArray();

        assertThat(entries.length, equalTo(2));
    }

    @Test
    public void exposesElements() throws SQLException {
        ReturningArrayOfCustomType returningArrayOfCustomType = new ReturningArrayOfCustomType(connection);
        ArrayOfCustomType actual = returningArrayOfCustomType.getValue();

        assertThat(actual.getElement(0).getLabel(), equalTo("hello"));
        assertThat(actual.getElement(1).getLabel(), equalTo("world"));
    }
}
