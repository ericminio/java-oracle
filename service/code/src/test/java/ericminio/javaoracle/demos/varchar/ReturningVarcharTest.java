package ericminio.javaoracle.demos.varchar;

import ericminio.javaoracle.data.DatabaseTest;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;

import static ericminio.javaoracle.data.Query.with;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class ReturningVarcharTest extends DatabaseTest {

    @Before
    public void seeds() {
        with(connection).execute("CREATE OR REPLACE PACKAGE returning_varchar\n" +
                "AS\n" +
                "\n" +
                "    FUNCTION get_value RETURN varchar2;\n" +
                "\n" +
                "END returning_varchar;");
        with(connection).execute("create or replace package body returning_varchar\n" +
                "AS\n" +
                "\n" +
                "    function get_value return varchar2\n" +
                "    as\n" +
                "    begin\n" +
                "        return '42';\n" +
                "    end;\n" +
                "\n" +
                "END returning_varchar;");
    }

    @Test
    public void works() throws SQLException {
        ReturningVarchar returningVarchar = new ReturningVarchar();
        returningVarchar.setConnection(connection);
        
        assertThat(returningVarchar.getValue(), equalTo("42"));
    }
}