package ericminio.javaoracle;

import ericminio.javaoracle.support.DatabaseTest;
import org.junit.Before;
import org.junit.Test;

import static ericminio.javaoracle.data.Query.with;
import static ericminio.javaoracle.support.FileUtils.contentOf;
import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;

public class GeneratePackageAdapterTest extends DatabaseTest {

    @Before
    public void createPackage() {
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
        with(connection).execute(creation);
    }

    @Test
    public void createsExpectedFile() throws Exception {
        new GeneratePackageAdapter().go("example", "examples", "target");
        String code = contentOf("target/Example.java");

        assertThat(code, containsString("public class Example {"));
        assertThat(code, containsString("   private Connection connection;"));
        assertThat(code, containsString("   public Example(Connection connection) {"));

        assertThat(code, containsString("   public Integer hello(String value1)"));
        assertThat(code, containsString("       statement.setString(1, value1);"));

        assertThat(code, containsString("   public String world(Integer value2)"));
        assertThat(code, containsString("       statement.setInt(1, value2);"));
    }
}
