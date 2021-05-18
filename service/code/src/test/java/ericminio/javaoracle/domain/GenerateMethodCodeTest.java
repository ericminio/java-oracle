package ericminio.javaoracle.domain;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class GenerateMethodCodeTest {

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
    public void disclosesReturnedType() throws IOException {
        generator.please(Arrays.asList("FUNCTION any_method RETURN any_type;"), "any_package", typeMapperFactory);

        assertThat(generator.getReturnType(), equalTo("any_type"));
    }

    @Test
    public void canCallFunctionWithoutParameter() throws IOException {
        String code = new GenerateMethodCode().please(Arrays.asList("FUNCTION any_method RETURN varchar2;"), "any_package", new TypeMapperFactory());
        assertThat(code, containsString("public String anyMethod() throws SQLException {\n"));
        assertThat(code, containsString("connection.prepareStatement(\"select any_package.any_method() from dual\");\n"));
    }

    @Test
    public void canReturnString() throws IOException {
        String code = new GenerateMethodCode().please(Arrays.asList("FUNCTION any_method RETURN varchar2;"), "any_package", new TypeMapperFactory());
        assertThat(code, containsString("public String anyMethod() throws SQLException {\n"));
        assertThat(code, containsString("    return (String) data;\n"));
    }

    @Test
    public void canReturnBigDecimal() throws IOException {
        String code = new GenerateMethodCode().please(Arrays.asList("FUNCTION any_method RETURN number;"), "any_package", new TypeMapperFactory());
        assertThat(code, containsString("public BigDecimal anyMethod() throws SQLException {\n"));
        assertThat(code, containsString("    return (BigDecimal) data;\n"));
    }

    @Test
    public void canReturnDate() throws IOException {
        String code = new GenerateMethodCode().please(Arrays.asList("FUNCTION any_method RETURN date;"), "any_package", new TypeMapperFactory());
        assertThat(code, containsString("public Date anyMethod() throws SQLException {\n"));
        assertThat(code, containsString("    return data == null ? null : new Date( ((java.sql.Timestamp) data).getTime() );\n"));
    }

    @Test
    public void canReturnCustomType() throws IOException {
        String code = new GenerateMethodCode().please(Arrays.asList("FUNCTION any_method RETURN any_type;"), "any_package", typeMapperFactory);
        assertThat(code, containsString("public AnyType anyMethod() throws SQLException {\n"));
        assertThat(code, containsString("    return (AnyType) data;\n"));
    }

    @Test
    public void canCallFunctionWithParameters() throws IOException {
        String code = new GenerateMethodCode().please(Arrays.asList("FUNCTION any_method(any_field1 number, any_field2 varchar2) RETURN any_type;"), "any_package", typeMapperFactory);
        assertThat(code, containsString("public AnyType anyMethod(BigDecimal anyField1, String anyField2) throws SQLException {\n"));
        assertThat(code, containsString("connection.prepareStatement(\"select any_package.any_method(?, ?) from dual\");\n"));
        assertThat(code, containsString("   statement.setBigDecimal(1, anyField1);\n"));
        assertThat(code, containsString("   statement.setString(2, anyField2);\n"));
        assertThat(code, containsString("    return (AnyType) data;\n"));
    }

    @Test
    public void supportOptionalInKeyword() throws IOException {
        String code = new GenerateMethodCode().please(Arrays.asList("FUNCTION any_method(any_field in any_type) RETURN any_type;"), "any_package", typeMapperFactory);
        assertThat(code, containsString("public AnyType anyMethod(AnyType anyField) throws SQLException {\n"));
    }

    @Test
    public void canReturnArrayType() throws IOException {
        List<String> functionSpecification = Arrays.asList("FUNCTION any_method RETURN array_type;");
        List<List<String>> typeSpecifications = Arrays.asList(
                Arrays.asList(
                        "create or replace type record_type as object(\n",
                        "   value number\n",
                        ");\n"),
                Arrays.asList("create or replace type array_type as table of record_type;")
        );
        TypeMapperFactory typeMapperFactory = new TypeMapperFactory(typeSpecifications);
        String code = new GenerateMethodCode().please(functionSpecification, "any_package", typeMapperFactory);
        assertThat(code, containsString("public ArrayType anyMethod() throws SQLException {\n"));
        assertThat(code, containsString("    return ArrayType.with((Object[]) ((java.sql.Array) data).getArray());\n"));
    }

    @Test
    public void canReturnCursor() throws IOException {
        List<String> functionSpecification = Arrays.asList("FUNCTION any_method RETURN any_cursor;");
        List<List<String>> typeSpecifications = Arrays.asList(
                Arrays.asList("type any_cursor as ref cursor;")
        );
        TypeMapperFactory typeMapperFactory = new TypeMapperFactory(typeSpecifications);
        String code = new GenerateMethodCode().please(functionSpecification, "any_package", typeMapperFactory);
        assertThat(code, containsString("public ResultSet anyMethod() throws SQLException {\n"));
        assertThat(code, containsString("    return (ResultSet) data;\n"));
    }
}
