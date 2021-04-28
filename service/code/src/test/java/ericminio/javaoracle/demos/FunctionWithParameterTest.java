package ericminio.javaoracle.demos;

import ericminio.javaoracle.support.DatabaseTest;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;

import static ericminio.javaoracle.data.Query.with;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class FunctionWithParameterTest extends DatabaseTest {

    @Before
    public void seeds() {
        with(connection).execute("CREATE OR REPLACE PACKAGE function_with_parameter\n" +
                "AS\n" +
                "\n" +
                "    FUNCTION count_by_type(\n" +
                "        value varchar2\n" +
                "    ) RETURN integer;\n" +
                "\n" +
                "    FUNCTION count_by_label(\n" +
                "        value varchar2\n" +
                "    ) RETURN integer;\n" +
                "\n" +
                "END function_with_parameter;");
        with(connection).execute("create or replace package body function_with_parameter\n" +
                "AS\n" +
                "\n" +
                "    function count_by_type(\n" +
                "        value varchar2\n" +
                "    ) return integer\n" +
                "    as\n" +
                "    begin\n" +
                "        return 15;\n" +
                "    end;\n" +
                "\n" +
                "\n" +
                "    function count_by_label(\n" +
                "        value varchar2\n" +
                "    ) return integer\n" +
                "    as\n" +
                "    begin\n" +
                "        return 42;\n" +
                "    end;\n" +
                "\n" +
                "END function_with_parameter;");
    }

    @Test
    public void countByType() throws SQLException {
        FunctionWithParameter functionWithParameter = new FunctionWithParameter(connection);
        
        assertThat(functionWithParameter.countByType("A"), equalTo(15));
    }

    @Test
    public void countByLabel() throws SQLException {
        FunctionWithParameter functionWithParameter = new FunctionWithParameter(connection);

        assertThat(functionWithParameter.countByLabel("any"), equalTo(42));
    }
}