package ericminio.javaoracle.demos.clob;

import ericminio.javaoracle.data.DatabaseTest;
import ericminio.javaoracle.support.Stringify;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.sql.Clob;
import java.sql.SQLException;

import static ericminio.javaoracle.data.Query.with;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class ReturningClobTest extends DatabaseTest {

    @Before
    public void seeds() {
        with(connection).execute("CREATE OR REPLACE PACKAGE returning_clob\n" +
                "AS\n" +
                "\n" +
                "    FUNCTION get_value RETURN clob;\n" +
                "\n" +
                "END returning_clob;");
        with(connection).execute("create or replace package body returning_clob\n" +
                "AS\n" +
                "\n" +
                "    function get_value return clob\n" +
                "    as\n" +
                "    begin\n" +
                "        return to_clob('this is a long clob');\n" +
                "    end;\n" +
                "\n" +
                "END returning_clob;");
    }

    @Test
    public void works() throws SQLException, IOException {
        ReturningClob returningClob = new ReturningClob(connection);
        Clob value = returningClob.getValue();
        String actual = new Stringify().inputStream(value.getAsciiStream());

        assertThat(actual, equalTo("this is a long clob"));
    }
}