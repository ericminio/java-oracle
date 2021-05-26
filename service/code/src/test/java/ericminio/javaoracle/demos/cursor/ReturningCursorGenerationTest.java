package ericminio.javaoracle.demos.cursor;

import ericminio.javaoracle.domain.GeneratePackageCode;
import ericminio.javaoracle.domain.TypeMapperFactory;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;

import static ericminio.javaoracle.support.FileUtils.contentMinusTwoFirstLines;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class ReturningCursorGenerationTest {

    @Test
    public void works() throws IOException {
        String actual = new GeneratePackageCode().please(Arrays.asList(
                "PACKAGE returning_cursor\n",
                "AS\n",
                "   TYPE product_cursor IS REF CURSOR;\n",
                "   FUNCTION get_products RETURN product_cursor;\n",
                "END returning_cursor;"
        ), new TypeMapperFactory(Arrays.asList(
                Arrays.asList("TYPE product_cursor IS REF CURSOR;")
        )));
        
        assertThat(actual, equalTo(contentMinusTwoFirstLines("src/test/java/ericminio/javaoracle/demos/cursor/ReturningCursor.java")));
    }

}