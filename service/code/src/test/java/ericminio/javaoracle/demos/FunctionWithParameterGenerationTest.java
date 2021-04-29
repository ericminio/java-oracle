package ericminio.javaoracle.demos;

import ericminio.javaoracle.domain.GenerateClassCode;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;

import static ericminio.javaoracle.support.FileUtils.contentMinusTwoFirstLines;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class FunctionWithParameterGenerationTest {

    @Test
    public void works() throws IOException {
        String actual = new GenerateClassCode().please(Arrays.asList(
                "PACKAGE function_with_parameter\n",
                "AS\n",
                "\n",
                "   FUNCTION count_by_type(\n",
                "       value varchar2\n",
                "   ) RETURN number;\n",
                "\n",
                "   FUNCTION count_by_label(\n",
                "       value varchar2\n",
                "   ) RETURN number;\n",
                "\n",
                "END function_with_parameter;"
        ));
        
        assertThat(actual, equalTo(contentMinusTwoFirstLines("src/test/java/ericminio/javaoracle/demos/FunctionWithParameter.java")));
    }

}