package ericminio.javaoracle.demos;

import ericminio.javaoracle.domain.GeneratePackageCode;
import ericminio.javaoracle.domain.TypeMapperFactory;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static ericminio.javaoracle.support.FileUtils.contentMinusTwoFirstLines;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class ReturningArrayOfCustomTypeGenerationTest {

    @Test
    public void works() throws IOException {
        List<String> packageSpecification = Arrays.asList(
                "PACKAGE returning_array_of_custom_type\n",
                "AS\n",
                "   FUNCTION get_value RETURN array_of_custom_type;\n",
                "END returning_array_of_custom_type;"
        );
        List<List<String>> typeSpecifications = Arrays.asList(
                Arrays.asList(
                        "create or replace type custom_type as object(\n",
                        "   value number\n",
                        ");\n"),
                Arrays.asList("create or replace type array_of_custom_type as table of custom_type;")
        );
        TypeMapperFactory typeMapperFactory = new TypeMapperFactory(typeSpecifications);
        String actual = new GeneratePackageCode().please(packageSpecification, typeMapperFactory);
        
        assertThat(actual, equalTo(contentMinusTwoFirstLines("src/test/java/ericminio/javaoracle/demos/ReturningArrayOfCustomType.java")));
    }

}