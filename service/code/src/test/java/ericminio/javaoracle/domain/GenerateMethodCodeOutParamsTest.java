package ericminio.javaoracle.domain;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;

public class GenerateMethodCodeOutParamsTest {

    private GenerateMethodCode generator;
    private TypeMapperFactory typeMapperFactory;

    @Before
    public void newGenerator() {
        generator = new GenerateMethodCode();
        List<List<String>> typeSpecifications = Arrays.asList(
                Arrays.asList("create type any_type as object(value number)")
        );
        typeMapperFactory = new TypeMapperFactory(typeSpecifications);
    }

    @Test
    public void outParameterIsMadeAvailableInArray() throws IOException {
        String code = new GenerateMethodCode().please(Arrays.asList("function any_method (value out varchar2) return number;"), "any_package", new TypeMapperFactory());
        assertThat(code, containsString("public BigDecimal anyMethod(String[] value) throws SQLException {\n"));
    }

    @Test
    public void outParameterMustBeRegistered() throws IOException {
        String code = new GenerateMethodCode().please(Arrays.asList("function any_method (value out varchar2) return number;"), "any_package", new TypeMapperFactory());
        assertThat(code, containsString("    statement.registerOutParameter(2, Types.VARCHAR);\n"));
    }

    @Test
    public void outParameterMustBeInitialized() throws IOException {
        String code = new GenerateMethodCode().please(Arrays.asList("function any_method (value out varchar2) return number;"), "any_package", new TypeMapperFactory());
        assertThat(code, containsString("    Object outValue = statement.getObject(2);\n"));
        assertThat(code, containsString("    value[0] = (String) outValue;\n"));
    }
}
