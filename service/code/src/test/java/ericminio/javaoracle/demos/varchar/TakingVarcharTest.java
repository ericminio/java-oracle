package ericminio.javaoracle.demos.varchar;

import ericminio.javaoracle.data.DatabaseTest;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;

import static ericminio.javaoracle.data.Query.with;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class TakingVarcharTest extends DatabaseTest {

    @Before
    public void seeds() {
        with(connection).execute("CREATE OR REPLACE PACKAGE taking_varchar\n" +
                "AS\n" +
                "\n" +
                "    FUNCTION get_value(\n" +
                "        input varchar2\n" +
                "    ) RETURN varchar2;\n" +
                "\n" +
                "END taking_varchar;");
        with(connection).execute("create or replace package body taking_varchar\n" +
                "AS\n" +
                "\n" +
                "    function get_value(\n" +
                "        input varchar2\n" +
                "    ) return varchar2\n" +
                "    as\n" +
                "    begin\n" +
                "        return 'Hello ' || input;\n" +
                "    end;\n" +
                "\n" +
                "END taking_varchar;");
    }

    @Test
    public void getValue() throws SQLException {
        TakingVarchar takingVarchar = new TakingVarchar(connection);

        assertThat(takingVarchar.getValue("Bob"), equalTo("Hello Bob"));
    }
}