package ericminio.javaoracle.demos;

import ericminio.javaoracle.domain.GenerateTypeCode;
import ericminio.javaoracle.domain.TypeMapperFactory;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static ericminio.javaoracle.support.FileUtils.contentMinusTwoFirstLines;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class CustomTypeNestingGenerationTest {

    @Test
    public void works() throws IOException {
        List<List<String>> typeSpecifications = Arrays.asList(
                Arrays.asList("create type custom_type as object(value number)")
        );
        TypeMapperFactory typeMapperFactory = new TypeMapperFactory(typeSpecifications);
        String actual = new GenerateTypeCode().please(Arrays.asList(
                "type custom_type_nesting as object\n",
                "(\n",
                "   nested custom_type\n" +
                ")"
        ), typeMapperFactory);
        assertThat(actual, equalTo(contentMinusTwoFirstLines("src/test/java/ericminio/javaoracle/demos/CustomTypeNesting.java")));
    }
}
