package ericminio.javaoracle.demos.custom;

import ericminio.javaoracle.domain.GenerateArrayTypeCode;
import ericminio.javaoracle.domain.TypeMapperFactory;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;

import static ericminio.javaoracle.support.FileUtils.contentMinusTwoFirstLines;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class ArrayOfCustomTypeGenerationTest {

    @Test
    public void worksWithVarray() throws IOException {
        TypeMapperFactory typeMapperFactory = new TypeMapperFactory(Arrays.asList(Arrays.asList(
                "create type custom_type as object(id number, label varchar2(15), creation_date date)"
        )));
        String actual = new GenerateArrayTypeCode().please(Arrays.asList("type array_of_custom_type as varray(15) of custom_type"), typeMapperFactory);
        assertThat(actual, equalTo(contentMinusTwoFirstLines("src/test/java/ericminio/javaoracle/demos/custom/ArrayOfCustomType.java")));
    }

    @Test
    public void worksWithTable() throws IOException {
        TypeMapperFactory typeMapperFactory = new TypeMapperFactory(Arrays.asList(Arrays.asList(
                "create type custom_type as object(id number, label varchar2(15), creation_date date)"
        )));
        String actual = new GenerateArrayTypeCode().please(Arrays.asList("type array_of_custom_type as table of custom_type"), typeMapperFactory);
        assertThat(actual, equalTo(contentMinusTwoFirstLines("src/test/java/ericminio/javaoracle/demos/custom/ArrayOfCustomType.java")));
    }
}
