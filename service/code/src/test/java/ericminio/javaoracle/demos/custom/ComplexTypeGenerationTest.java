package ericminio.javaoracle.demos.custom;

import ericminio.javaoracle.domain.GenerateTypeCode;
import ericminio.javaoracle.domain.TypeMapperFactory;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static ericminio.javaoracle.support.FileUtils.contentMinusTwoFirstLines;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class ComplexTypeGenerationTest {

    @Test
    public void works() throws IOException {
        List<List<String>> typeSpecifications = Arrays.asList(
                Arrays.asList(
                        "create or replace type custom_type as object(\n",
                        "   value number\n",
                        ");\n"),
                Arrays.asList("create or replace type array_of_custom_type as table of custom_type;")
        );
        TypeMapperFactory typeMapperFactory = new TypeMapperFactory(typeSpecifications);
        String actual = new GenerateTypeCode().please(Arrays.asList(
                "type complex_type as object\n",
                "(\n",
                "   value custom_type,\n" +
                "   collection array_of_custom_type\n" +
                ")"
        ), typeMapperFactory);
        assertThat(actual, equalTo(contentMinusTwoFirstLines("src/test/java/ericminio/javaoracle/demos/custom/ComplexType.java")));
    }
}
