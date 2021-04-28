package ericminio.javaoracle;

import ericminio.javaoracle.support.DatabaseTest;
import org.junit.Before;
import org.junit.Test;

import static ericminio.javaoracle.support.FileUtils.contentOf;
import static ericminio.javaoracle.data.Query.with;
import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;

public class GenerateTypeAdapterTest extends DatabaseTest {

    @Before
    public void createType() {
        String creation = "" +
                "create or replace type custom_type as object\n" +
                "(\n" +
                "   value integer\n" +
                ");";
        with(connection).execute(creation);
    }

    @Test
    public void createsExpectedFile() throws Exception {
        new GenerateTypeAdapter().go("custom_type", "examples", "target");
        String code = contentOf("target/CustomType.java");

        assertThat(code, containsString("public class CustomType implements SQLData {"));
    }
}
