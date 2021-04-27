package ericminio.javaoracle.domain;

import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class GenerateMethodCodeTest {

    @Test
    public void canReturnInteger() throws IOException {
        String code = new GenerateMethodCode().please(Arrays.asList(
                "FUNCTION any_method RETURN integer;"
        ), "any_package");
        assertThat(code, equalTo("" +
                "    public Integer anyMethod() throws SQLException {\n" +
                "        PreparedStatement statement = connection.prepareStatement(\"select any_package.any_method() from dual\");\n" +
                "        ResultSet resultSet = statement.executeQuery();\n" +
                "        resultSet.next();\n" +
                "\n" +
                "        return resultSet.getInt(1);\n" +
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
                "        return resultSet.getString(1);\n" +
                "    }"));
    }

    @Test
    public void canHaveParameters() throws IOException {
        String code = new GenerateMethodCode().please(Arrays.asList(
                "FUNCTION any_method(field1 integer, field2 varchar2) RETURN varchar2;"
        ), "any_package");
        assertThat(code, equalTo("" +
                "    public String anyMethod(Integer field1, String field2) throws SQLException {\n" +
                "        PreparedStatement statement = connection.prepareStatement(\"select any_package.any_method(?, ?) from dual\");\n" +
                "        statement.setInt(1, field1);\n" +
                "        statement.setString(2, field2);\n" +
                "        ResultSet resultSet = statement.executeQuery();\n" +
                "        resultSet.next();\n" +
                "\n" +
                "        return resultSet.getString(1);\n" +
                "    }"));
    }
}
