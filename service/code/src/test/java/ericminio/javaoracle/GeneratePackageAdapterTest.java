package ericminio.javaoracle;

import ericminio.javaoracle.support.DatabaseTest;
import org.junit.Before;
import org.junit.Test;

import static ericminio.javaoracle.data.Query.with;
import static ericminio.javaoracle.support.FileUtils.exactContentOf;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.startsWith;
import static org.junit.Assert.assertThat;

public class GeneratePackageAdapterTest extends DatabaseTest {

    @Before
    public void createPackage() {
        String creation = "CREATE OR REPLACE PACKAGE example\n" +
                "AS\n" +
                "    FUNCTION hello(value1 varchar2) RETURN number;\n" +
                "    FUNCTION world(value2 number) RETURN varchar2;\n" +
                "    FUNCTION yop RETURN example_type;\n" +
                "END example";
        with(connection).execute(creation);
    }

    @Test
    public void createsExpectedFile() throws Exception {
        new GeneratePackageAdapter().go("example", "examples", "target");
        String code = exactContentOf("target/Example.java");

        assertThat(code, startsWith("" +
                "package examples;\n" +
                "\n" +
                "import java.math.BigDecimal;\n"
        ));
        assertThat(code, containsString("public class Example {"));
        assertThat(code, containsString("   private Connection connection;"));
        assertThat(code, containsString("   public Example(Connection connection) throws SQLException {"));

        assertThat(code, containsString("   public BigDecimal hello(String value1)"));
        assertThat(code, containsString("       statement.setString(1, value1);"));

        assertThat(code, containsString("   public String world(BigDecimal value2)"));
        assertThat(code, containsString("       statement.setBigDecimal(1, value2);"));
    }
}
