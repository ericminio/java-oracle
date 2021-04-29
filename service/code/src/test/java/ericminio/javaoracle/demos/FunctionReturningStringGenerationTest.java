package ericminio.javaoracle.demos;

import ericminio.javaoracle.domain.GenerateClassCode;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;

import static ericminio.javaoracle.support.FileUtils.contentMinusTwoFirstLines;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class FunctionReturningStringGenerationTest {

    @Test
    public void works() throws IOException {
        String actual = new GenerateClassCode().please(Arrays.asList(
                "PACKAGE function_returning_string\n",
                "AS\n",
                "   FUNCTION get_value RETURN varchar2;\n",
                "END function_returning_string;"
        ));
        
        assertThat(actual, equalTo(contentMinusTwoFirstLines("src/test/java/ericminio/javaoracle/demos/FunctionReturningString.java")));
    }

}