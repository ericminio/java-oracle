package ericminio.javaoracle.demos.clob;

import ericminio.javaoracle.domain.GeneratePackageCode;
import ericminio.javaoracle.domain.TypeMapperFactory;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;

import static ericminio.javaoracle.support.FileUtils.contentMinusTwoFirstLines;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class ReturningClobGenerationTest {

    @Test
    public void works() throws IOException {
        String actual = new GeneratePackageCode().please(Arrays.asList(
                "PACKAGE returning_clob\n",
                "AS\n",
                "   FUNCTION get_value RETURN clob;\n",
                "END returning_clob;"
        ), new TypeMapperFactory());
        
        assertThat(actual, equalTo(contentMinusTwoFirstLines("src/test/java/ericminio/javaoracle/demos/clob/ReturningClob.java")));
    }

}