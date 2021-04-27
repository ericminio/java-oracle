package ericminio.javaoracle;

import ericminio.javaoracle.support.Database;
import org.junit.Before;
import org.junit.Test;

import static ericminio.javaoracle.support.FileUtils.contentOf;
import static ericminio.javaoracle.support.Query.with;
import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;

public class GeneratorTest {

    @Before
    public void createPackage() throws Exception {
        String creation = "CREATE OR REPLACE PACKAGE example\n" +
                "AS\n" +
                "\n" +
                "    FUNCTION hello(\n" +
                "        value1 varchar2\n" +
                "    ) RETURN integer;\n" +
                "\n" +
                "    FUNCTION world(\n" +
                "        value2 integer\n" +
                "    ) RETURN varchar2;\n" +
                "\n" +
                "END example";
        with(new Database().connection()).execute(creation);
    }

    @Test
    public void createsExpectedFile() throws Exception {
        new Generator().go("example", "examples", "target");
        String code = contentOf("target/Example.java");

        assertThat(code, containsString("public class Example {"));

        assertThat(code, containsString("public int hello(String value1)"));
        assertThat(code, containsString("statement.registerOutParameter(1, Types.INTEGER);"));
        assertThat(code, containsString("statement.setString(2, value1);"));

        assertThat(code, containsString("public String world(int value2)"));
        assertThat(code, containsString("statement.registerOutParameter(1, Types.VARCHAR);"));
        assertThat(code, containsString("statement.setInt(2, value2);"));
    }
}
