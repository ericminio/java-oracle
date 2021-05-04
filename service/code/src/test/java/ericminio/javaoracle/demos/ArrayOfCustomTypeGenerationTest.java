package ericminio.javaoracle.demos;

import ericminio.javaoracle.domain.GenerateArrayTypeCode;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;

import static ericminio.javaoracle.support.FileUtils.contentMinusTwoFirstLines;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class ArrayOfCustomTypeGenerationTest {

    @Test
    public void worksWithVarray() throws IOException {
        String actual = new GenerateArrayTypeCode().please(Arrays.asList("type array_of_custom_type as varray(15) of custom_type"));
        assertThat(actual, equalTo(contentMinusTwoFirstLines("src/test/java/ericminio/javaoracle/demos/ArrayOfCustomType.java")));
    }

    @Test
    public void worksWithTable() throws IOException {
        String actual = new GenerateArrayTypeCode().please(Arrays.asList("type array_of_custom_type as table of custom_type"));
        assertThat(actual, equalTo(contentMinusTwoFirstLines("src/test/java/ericminio/javaoracle/demos/ArrayOfCustomType.java")));
    }
}
