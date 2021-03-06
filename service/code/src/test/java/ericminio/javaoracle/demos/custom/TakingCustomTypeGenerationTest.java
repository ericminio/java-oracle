package ericminio.javaoracle.demos.custom;

import ericminio.javaoracle.domain.GeneratePackageCode;
import ericminio.javaoracle.domain.TypeMapperFactory;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;

import static ericminio.javaoracle.support.FileUtils.contentMinusTwoFirstLines;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class TakingCustomTypeGenerationTest {

    @Test
    public void works() throws IOException {
        String actual = new GeneratePackageCode().please(Arrays.asList(
                "PACKAGE taking_custom_type\n",
                "AS\n",
                "\n",
                "   FUNCTION get_value(\n",
                "       input custom_type\n",
                "   ) RETURN custom_type;\n",
                "\n",
                "END taking_custom_type;"
        ), new TypeMapperFactory(Arrays.asList(
                Arrays.asList("create type custom_type as object(value number)")
        )));
        
        assertThat(actual, equalTo(contentMinusTwoFirstLines("src/test/java/ericminio/javaoracle/demos/custom/TakingCustomType.java")));
    }

}