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
                "    public int anyMethod() throws SQLException {\n" +
                "        CallableStatement statement = connection.prepareCall(\"{? = call any_package.any_method()}\");\n" +
                "        statement.registerOutParameter(1, Types.INTEGER);\n" +
                "        statement.execute();\n" +
                "\n" +
                "        return statement.getInt(1);\n" +
                "    }"));
    }

    @Test
    public void canReturnString() throws IOException {
        String code = new GenerateMethodCode().please(Arrays.asList(
                "FUNCTION any_method RETURN varchar2;"
        ), "any_package");
        assertThat(code, equalTo("" +
                "    public String anyMethod() throws SQLException {\n" +
                "        CallableStatement statement = connection.prepareCall(\"{? = call any_package.any_method()}\");\n" +
                "        statement.registerOutParameter(1, Types.VARCHAR);\n" +
                "        statement.execute();\n" +
                "\n" +
                "        return statement.getString(1);\n" +
                "    }"));
    }

    @Test
    public void canHaveParameters() throws IOException {
        String code = new GenerateMethodCode().please(Arrays.asList(
                "FUNCTION any_method(field1 integer, field2 varchar2) RETURN varchar2;"
        ), "any_package");
        assertThat(code, equalTo("" +
                "    public String anyMethod(int field1, String field2) throws SQLException {\n" +
                "        CallableStatement statement = connection.prepareCall(\"{? = call any_package.any_method(?, ?)}\");\n" +
                "        statement.registerOutParameter(1, Types.VARCHAR);\n" +
                "        statement.setInt(2, field1);\n" +
                "        statement.setString(3, field2);\n" +
                "        statement.execute();\n" +
                "\n" +
                "        return statement.getString(1);\n" +
                "    }"));
    }
}
