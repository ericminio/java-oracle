package ericminio.javaoracle.demos;

import ericminio.javaoracle.domain.GenerateClassCode;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;

import static ericminio.javaoracle.support.FileUtils.contentMinusTwoFirstLines;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class TakingNoParameterGenerationTest {

    @Test
    public void works() throws IOException {
        String actual = new GenerateClassCode().please(Arrays.asList(
                "PACKAGE taking_no_parameter\n",
                "AS\n",
                "   FUNCTION get_value RETURN number;\n",
                "END taking_no_parameter;"
        ));
        
        assertThat(actual, equalTo(contentMinusTwoFirstLines("src/test/java/ericminio/javaoracle/demos/TakingNoParameter.java")));
    }

}