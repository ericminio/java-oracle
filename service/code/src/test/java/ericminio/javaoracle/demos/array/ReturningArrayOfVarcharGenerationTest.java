package ericminio.javaoracle.demos.array;

import ericminio.javaoracle.data.DatabaseTest;
import ericminio.javaoracle.domain.GeneratePackageCode;
import ericminio.javaoracle.domain.TypeMapperFactory;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;

import static ericminio.javaoracle.support.FileUtils.contentMinusTwoFirstLines;
import static org.hamcrest.CoreMatchers.equalTo;

public class ReturningArrayOfVarcharGenerationTest extends DatabaseTest {

    @Test
    public void works() throws IOException {
        TypeMapperFactory typeMapperFactory = new TypeMapperFactory(Arrays.asList(
                Arrays.asList("create or replace type array_of_varchar as varray(5) of varchar2(15);")));
        String actual = new GeneratePackageCode().please(Arrays.asList(
                "create or replace package returning_array_of_varchar\n",
                "AS\n",
                "    FUNCTION get_value RETURN array_of_varchar;\n",
                "END returning_array_of_varchar;"), typeMapperFactory);

        Assert.assertThat(actual, equalTo(contentMinusTwoFirstLines("src/test/java/ericminio/javaoracle/demos/array/ReturningArrayOfVarchar.java")));
    }
}
