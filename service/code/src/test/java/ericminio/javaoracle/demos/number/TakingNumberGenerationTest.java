package ericminio.javaoracle.demos.number;

import ericminio.javaoracle.domain.GeneratePackageCode;
import ericminio.javaoracle.domain.TypeMapperFactory;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;

import static ericminio.javaoracle.support.FileUtils.contentMinusTwoFirstLines;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class TakingNumberGenerationTest {

    @Test
    public void works() throws IOException {
        String actual = new GeneratePackageCode().please(Arrays.asList(
                "PACKAGE taking_number\n",
                "AS\n",
                "\n",
                "   FUNCTION get_value(\n",
                "       input number\n",
                "   ) RETURN number;\n",
                "\n",
                "END taking_number;"
        ), new TypeMapperFactory());
        
        assertThat(actual, equalTo(contentMinusTwoFirstLines("src/test/java/ericminio/javaoracle/demos/number/TakingNumber.java")));
    }

}