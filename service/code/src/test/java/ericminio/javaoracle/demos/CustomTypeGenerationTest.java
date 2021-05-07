package ericminio.javaoracle.demos;

import ericminio.javaoracle.domain.GenerateTypeCode;
import ericminio.javaoracle.domain.TypeMapperFactory;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;

import static ericminio.javaoracle.support.FileUtils.contentMinusTwoFirstLines;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class CustomTypeGenerationTest {

    @Test
    public void works() throws IOException {
        String actual = new GenerateTypeCode().please(Arrays.asList(
                "type custom_type as object\n",
                "(\n",
                "   id number,\n" +
                "   label varchar2(15),\n" +
                "   creation_date date\n" +
                ")"
        ), new TypeMapperFactory());
        assertThat(actual, equalTo(contentMinusTwoFirstLines("src/test/java/ericminio/javaoracle/demos/CustomType.java")));
    }
}
