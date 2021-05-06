package ericminio.javaoracle.demos;

import ericminio.javaoracle.domain.GenerateClassCode;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;

import static ericminio.javaoracle.support.FileUtils.contentMinusTwoFirstLines;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class ReturningDateGenerationTest {

    @Test
    public void works() throws IOException {
        String actual = new GenerateClassCode().please(Arrays.asList(
                "PACKAGE returning_date\n",
                "AS\n",
                "   FUNCTION get_value RETURN date;\n",
                "END returning_date;"
        ));
        
        assertThat(actual, equalTo(contentMinusTwoFirstLines("src/test/java/ericminio/javaoracle/demos/ReturningDate.java")));
    }

}