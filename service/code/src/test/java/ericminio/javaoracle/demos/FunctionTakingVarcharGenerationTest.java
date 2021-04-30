package ericminio.javaoracle.demos;

import ericminio.javaoracle.domain.GenerateClassCode;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;

import static ericminio.javaoracle.support.FileUtils.contentMinusTwoFirstLines;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class FunctionTakingVarcharGenerationTest {

    @Test
    public void works() throws IOException {
        String actual = new GenerateClassCode().please(Arrays.asList(
                "PACKAGE function_taking_varchar\n",
                "AS\n",
                "\n",
                "   FUNCTION get_value(\n",
                "       input varchar2\n",
                "   ) RETURN varchar2;\n",
                "\n",
                "END function_taking_varchar;"
        ));
        
        assertThat(actual, equalTo(contentMinusTwoFirstLines("src/test/java/ericminio/javaoracle/demos/FunctionTakingVarchar.java")));
    }

}