package ericminio.javaoracle.demos;

import ericminio.javaoracle.domain.GeneratePackageCode;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;

import static ericminio.javaoracle.support.FileUtils.contentMinusTwoFirstLines;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class TakingDateGenerationTest {

    @Test
    public void works() throws IOException {
        String actual = new GeneratePackageCode().please(Arrays.asList(
                "PACKAGE taking_date\n",
                "AS\n",
                "\n",
                "   FUNCTION get_value(\n",
                "       input date\n",
                "   ) RETURN date;\n",
                "\n",
                "END taking_date;"
        ));
        
        assertThat(actual, equalTo(contentMinusTwoFirstLines("src/test/java/ericminio/javaoracle/demos/TakingDate.java")));
    }

}