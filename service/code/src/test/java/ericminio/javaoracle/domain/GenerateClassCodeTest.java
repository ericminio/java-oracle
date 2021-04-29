package ericminio.javaoracle.domain;

import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class GenerateClassCodeTest {

    @Test
    public void disclosesPackageName() throws IOException {
        GenerateClassCode generateClassCode = new GenerateClassCode();
        generateClassCode.please(Arrays.asList(
                "PACKAGE EXPLORATION\n",
                "AS\n",
                "   FUNCTION get_event_count RETURN INTEGER;\n",
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
                "   FUNCTION any_function RETURN integer;\n",
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
}
