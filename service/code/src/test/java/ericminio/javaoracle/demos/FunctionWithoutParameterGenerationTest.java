package ericminio.javaoracle.demos;

import ericminio.javaoracle.domain.GenerateClassCode;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;

import static ericminio.javaoracle.support.FileUtils.contentOf;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class FunctionWithoutParameterGenerationTest {

    @Test
    public void works() throws IOException {
        String actual = new GenerateClassCode().please(Arrays.asList(
                "PACKAGE function_without_parameter\n",
                "AS\n",
                "   FUNCTION get_value RETURN integer;\n",
                "END function_without_parameter;"
        ));
        
        assertThat(actual, equalTo(contentOf("src/main/java/ericminio/javaoracle/demos/FunctionWithoutParameter.java")));
    }

}