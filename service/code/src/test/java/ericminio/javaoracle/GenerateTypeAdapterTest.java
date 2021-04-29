package ericminio.javaoracle;

import ericminio.javaoracle.support.DatabaseTest;
import org.junit.Before;
import org.junit.Test;

import static ericminio.javaoracle.data.Query.with;
import static ericminio.javaoracle.support.FileUtils.exactContentOf;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.startsWith;
import static org.junit.Assert.assertThat;

public class GenerateTypeAdapterTest extends DatabaseTest {

    @Before
    public void createType() {
        String creation = "" +
                "create or replace type example_type as object(value number);";
        with(connection).execute(creation);
    }

    @Test
    public void createsExpectedFile() throws Exception {
        new GenerateTypeAdapter().go("example_type", "examples", "target");
        String code = exactContentOf("target/ExampleType.java");

        assertThat(code, startsWith("" +
                "package examples;\n" +
                "\n" +
                "import java.math.BigDecimal;\n"
        ));
        assertThat(code, containsString("" +
                "public class ExampleType implements SQLData {"));
    }
}
