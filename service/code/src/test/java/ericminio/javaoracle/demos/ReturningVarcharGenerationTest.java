package ericminio.javaoracle.demos;

import ericminio.javaoracle.domain.GeneratePackageCode;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;

import static ericminio.javaoracle.support.FileUtils.contentMinusTwoFirstLines;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class ReturningVarcharGenerationTest {

    @Test
    public void works() throws IOException {
        String actual = new GeneratePackageCode().please(Arrays.asList(
                "PACKAGE returning_varchar\n",
                "AS\n",
                "   FUNCTION get_value RETURN varchar2;\n",
                "END returning_varchar;"
        ));
        
        assertThat(actual, equalTo(contentMinusTwoFirstLines("src/test/java/ericminio/javaoracle/demos/ReturningVarchar.java")));
    }

}