package ericminio.javaoracle.demos;

import ericminio.javaoracle.domain.GenerateTypeCode;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;

import static ericminio.javaoracle.support.FileUtils.contentOf;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class CustomTypeGenerationTest {
    
    @Test
    public void works() throws IOException {
        String actual = new GenerateTypeCode().please(Arrays.asList(
                "type custom_type as object\n",
                "(\n",
                "   value integer\n",
                ")"
        ));
        assertThat(actual, equalTo(contentOf("src/test/java/ericminio/javaoracle/demos/CustomType.java")));
    }
}
