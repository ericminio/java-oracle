package ericminio.javaoracle.domain;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class GenerateMethodCodeTest {

    private GenerateMethodCode generator;

    @Before
    public void newGenerator() {
        generator = new GenerateMethodCode();
    }

    @Test
    public void disclosesReturnedType() throws IOException {
        generator.please(Arrays.asList("FUNCTION any_method RETURN any_type;"), "any_package");

        assertThat(generator.getReturnType(), equalTo("any_type"));
    }

    @Test
    public void canCallFunctionWithoutParameter() throws IOException {
        String code = new GenerateMethodCode().please(Arrays.asList("FUNCTION any_method RETURN varchar2;"), "any_package");
        assertThat(code, containsString("public String anyMethod() throws SQLException {\n"));
        assertThat(code, containsString("connection.prepareStatement(\"select any_package.any_method() from dual\");\n"));
    }

    @Test
    public void canReturnInteger() throws IOException {
        String code = new GenerateMethodCode().please(Arrays.asList("FUNCTION any_method RETURN varchar2;"), "any_package");
        assertThat(code, containsString("public String anyMethod() throws SQLException {\n"));
        assertThat(code, containsString("    return (String) resultSet.getObject(1);\n"));
    }

    @Test
    public void canReturnString() throws IOException {
        String code = new GenerateMethodCode().please(Arrays.asList("FUNCTION any_method RETURN number;"), "any_package");
        assertThat(code, containsString("public BigDecimal anyMethod() throws SQLException {\n"));
        assertThat(code, containsString("    return (BigDecimal) resultSet.getObject(1);\n"));
    }

    @Test
    public void canReturnCustomType() throws IOException {
        String code = new GenerateMethodCode().please(Arrays.asList("FUNCTION any_method RETURN any_type;"), "any_package");
        assertThat(code, containsString("public AnyType anyMethod() throws SQLException {\n"));
        assertThat(code, containsString("    return (AnyType) resultSet.getObject(1);\n"));
    }

    @Test
    public void canCallFunctionWithParameters() throws IOException {
        String code = new GenerateMethodCode().please(Arrays.asList("FUNCTION any_method(any_field1 number, any_field2 varchar2) RETURN any_type;"), "any_package");
        assertThat(code, containsString("public AnyType anyMethod(BigDecimal anyField1, String anyField2) throws SQLException {\n"));
        assertThat(code, containsString("connection.prepareStatement(\"select any_package.any_method(?, ?) from dual\");\n"));
        assertThat(code, containsString("   statement.setBigDecimal(1, anyField1);\n"));
        assertThat(code, containsString("   statement.setString(2, anyField2);\n"));
        assertThat(code, containsString("    return (AnyType) resultSet.getObject(1);\n"));
    }

    @Test
    public void supportOptionalInKeyword() throws IOException {
        String code = new GenerateMethodCode().please(Arrays.asList("FUNCTION any_method(any_field in any_type) RETURN any_type;"), "any_package");
        assertThat(code, containsString("public AnyType anyMethod(AnyType anyField) throws SQLException {\n"));
    }
}
