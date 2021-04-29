package ericminio.javaoracle.domain;

import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

public class GenerateClassCodeTest {

    @Test
    public void disclosesPackageName() throws IOException {
        GenerateClassCode generateClassCode = new GenerateClassCode();
        generateClassCode.please(Arrays.asList(
                "PACKAGE EXPLORATION\n",
                "AS\n",
                "   FUNCTION get_event_count RETURN number;\n",
                "END EXPLORATION;"
        ));
        assertThat(generateClassCode.getPackageName(), equalTo("exploration"));
    }

    @Test
    public void generatesExpectedConstructor() throws IOException {
        GenerateClassCode generateClassCode = new GenerateClassCode();
        String code = generateClassCode.please(Arrays.asList(
                "PACKAGE any_package\n",
                "AS\n",
                "   FUNCTION any_function RETURN number;\n",
                "END EXPLORATION;"
        ));
        assertThat(code, containsString("" +
                "    public AnyPackage(Connection connection) {\n" +
                "        this.connection = connection;\n" +
                "    }"
        ));
    }

    @Test
    public void generatesTypeMappingWhenFunctionReturnsCustomType() throws IOException {
        GenerateClassCode generateClassCode = new GenerateClassCode();
        String code = generateClassCode.please(Arrays.asList(
                "PACKAGE any_package\n",
                "AS\n",
                "   FUNCTION any_function RETURN any_type;\n",
                "END EXPLORATION;"
        ));
        assertThat(code, containsString("" +
                "    public AnyPackage(Connection connection) throws SQLException {\n" +
                "        this.connection = connection;\n" +
                "        connection.getTypeMap().put(AnyType.NAME, AnyType.class);\n" +
                "    }"
        ));
    }

    @Test
    public void importsArePreservedByDefault() throws IOException {
        GenerateClassCode generateClassCode = new GenerateClassCode();
        String code = generateClassCode.please(Arrays.asList(
                "PACKAGE any_package\n",
                "AS\n",
                "   FUNCTION any_function RETURN any_type;\n",
                "END EXPLORATION;"
        ));
        assertThat(code, not(containsString("import java.math.BigDecimal;\n")));
    }

    @Test
    public void importsAreUpdatedIfBigDecimalIsUsedAsReturnType() throws IOException {
        GenerateClassCode generateClassCode = new GenerateClassCode();
        String code = generateClassCode.please(Arrays.asList(
                "PACKAGE any_package\n",
                "AS\n",
                "   FUNCTION any_function RETURN number;\n",
                "END EXPLORATION;"
        ));
        assertThat(code, containsString("import java.math.BigDecimal;\n"));
    }

    @Test
    public void importsAreUpdatedIfBigDecimalIsUsedAsParameter() throws IOException {
        GenerateClassCode generateClassCode = new GenerateClassCode();
        String code = generateClassCode.please(Arrays.asList(
                "PACKAGE any_package\n",
                "AS\n",
                "   FUNCTION any_function(param number) RETURN any_type;\n",
                "END EXPLORATION;"
        ));
        assertThat(code, containsString("import java.math.BigDecimal;\n"));
    }
}
