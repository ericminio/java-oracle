package ericminio.javaoracle.domain;

import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class GenerateMethodCodeTest {

    @Test
    public void disclosesReturnedType() throws IOException {
        GenerateMethodCode generator = new GenerateMethodCode();
        generator.please(Arrays.asList("FUNCTION any_method RETURN any_type;"), "any_package");

        assertThat(generator.getReturnType(), equalTo("any_type"));
    }

    @Test
    public void canReturnInteger() throws IOException {
        String code = new GenerateMethodCode().please(Arrays.asList(
                "FUNCTION any_method RETURN number;"
        ), "any_package");
        assertThat(code, equalTo("" +
                "    public BigDecimal anyMethod() throws SQLException {\n" +
                "        PreparedStatement statement = connection.prepareStatement(\"select any_package.any_method() from dual\");\n" +
                "        ResultSet resultSet = statement.executeQuery();\n" +
                "        resultSet.next();\n" +
                "\n" +
                "        return (BigDecimal) resultSet.getObject(1);\n" +
                "    }"));
    }

    @Test
    public void canReturnString() throws IOException {
        String code = new GenerateMethodCode().please(Arrays.asList(
                "FUNCTION any_method RETURN varchar2;"
        ), "any_package");
        assertThat(code, equalTo("" +
                "    public String anyMethod() throws SQLException {\n" +
                "        PreparedStatement statement = connection.prepareStatement(\"select any_package.any_method() from dual\");\n" +
                "        ResultSet resultSet = statement.executeQuery();\n" +
                "        resultSet.next();\n" +
                "\n" +
                "        return (String) resultSet.getString(1);\n" +
                "    }"));
    }

    @Test
    public void canHaveParameters() throws IOException {
        String code = new GenerateMethodCode().please(Arrays.asList(
                "FUNCTION any_method(field1 number, field2 varchar2) RETURN varchar2;"
        ), "any_package");
        assertThat(code, equalTo("" +
                "    public String anyMethod(BigDecimal field1, String field2) throws SQLException {\n" +
                "        PreparedStatement statement = connection.prepareStatement(\"select any_package.any_method(?, ?) from dual\");\n" +
                "        statement.setBigDecimal(1, field1);\n" +
                "        statement.setString(2, field2);\n" +
                "        ResultSet resultSet = statement.executeQuery();\n" +
                "        resultSet.next();\n" +
                "\n" +
                "        return (String) resultSet.getString(1);\n" +
                "    }"));
    }

    @Test
    public void canReturnCustomType() throws IOException {
        String code = new GenerateMethodCode().please(Arrays.asList(
                "FUNCTION any_method RETURN any_type;"
        ), "any_package");
        assertThat(code, equalTo("" +
                "    public AnyType anyMethod() throws SQLException {\n" +
                "        PreparedStatement statement = connection.prepareStatement(\"select any_package.any_method() from dual\");\n" +
                "        ResultSet resultSet = statement.executeQuery();\n" +
                "        resultSet.next();\n" +
                "\n" +
                "        return (AnyType) resultSet.getObject(1);\n" +
                "    }"));
    }
}
