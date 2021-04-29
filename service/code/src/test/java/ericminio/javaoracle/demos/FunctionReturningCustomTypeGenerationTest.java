package ericminio.javaoracle.demos;

import ericminio.javaoracle.domain.GenerateClassCode;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;

import static ericminio.javaoracle.support.FileUtils.contentOf;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class FunctionReturningCustomTypeGenerationTest {

    @Test
    public void works() throws IOException {
        String actual = new GenerateClassCode().please(Arrays.asList(
                "PACKAGE function_returning_custom_type\n",
                "AS\n",
                "   FUNCTION get_value RETURN custom_type;\n",
                "END function_returning_custom_type;"
        ));
        
        assertThat(actual, equalTo(contentOf("src/test/java/ericminio/javaoracle/demos/FunctionReturningCustomType.java")));
    }

}