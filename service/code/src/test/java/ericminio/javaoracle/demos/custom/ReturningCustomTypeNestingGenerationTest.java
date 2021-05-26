package ericminio.javaoracle.demos.custom;

import ericminio.javaoracle.domain.GeneratePackageCode;
import ericminio.javaoracle.domain.TypeMapperFactory;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static ericminio.javaoracle.support.FileUtils.contentMinusTwoFirstLines;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class ReturningCustomTypeNestingGenerationTest {

    @Test
    public void works() throws IOException {
        List<List<String>> typeSpecifications = Arrays.asList(
            Arrays.asList("create type custom_type as object (id number, label varchar2(15), creation_date date);"),
            Arrays.asList("create type custom_type_nesting as object (nested custom_type);")
        );
        List<String> packageSpecification = Arrays.asList(
                "package returning_custom_type_nesting as",
                "   function get_value return custom_type_nesting;",
                "end returning_custom_type_nesting;"
        );
        String actual = new GeneratePackageCode().please(packageSpecification, new TypeMapperFactory(typeSpecifications));
        
        assertThat(actual, equalTo(contentMinusTwoFirstLines("src/test/java/ericminio/javaoracle/demos/custom/ReturningCustomTypeNesting.java")));
    }

}