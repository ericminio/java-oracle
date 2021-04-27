package ericminio.javaoracle.domain;

import ericminio.javaoracle.domain.ExtractFunctionParameters;
import ericminio.javaoracle.domain.Parameters;
import org.junit.Test;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class ExtractFunctionParametersTest {

    @Test
    public void works() {
        Parameters parameters = new ExtractFunctionParameters().please(Arrays.asList(
            "FUNCTION get_event_count RETURN INTEGER;"
        ));
        assertThat(parameters.size(), equalTo(0));
        assertThat(parameters.toList(), equalTo(""));
    }

    @Test
    public void resistsParenthesis() {
        Parameters parameters = new ExtractFunctionParameters().please(Arrays.asList(
                "FUNCTION get_event_count() RETURN INTEGER;"
        ));
        assertThat(parameters.size(), equalTo(0));
        assertThat(parameters.toList(), equalTo(""));
    }

    @Test
    public void resistsParameter() {
        Parameters parameters = new ExtractFunctionParameters().please(Arrays.asList(
                "FUNCTION any(param integer) RETURN INTEGER;"
        ));
        assertThat(parameters.size(), equalTo(1));
        assertThat(parameters.toList(), equalTo("int param"));
    }

    @Test
    public void resistsMultiline() {
        Parameters parameters = new ExtractFunctionParameters().please(Arrays.asList(
                "FUNCTION any(",
                "   field1 INTEGER,",
                "   field2 integer,",
                "   field3 varchar2,",
                "   field4 VARCHAR2",
                ") RETURN INTEGER;"
        ));
        assertThat(parameters.size(), equalTo(4));
        assertThat(parameters.toList(), equalTo("int field1, int field2, String field3, String field4"));
    }

    @Test
    public void resistsOpeningParenthesisOnNewLine() {
        Parameters parameters = new ExtractFunctionParameters().please(Arrays.asList(
                "FUNCTION any",
                "(",
                "   param integer",
                ") RETURN INTEGER;"
        ));
        assertThat(parameters.size(), equalTo(1));
        assertThat(parameters.toList(), equalTo("int param"));
    }

    @Test
    public void resistsReturnTypeOnNewLine() {
        Parameters parameters = new ExtractFunctionParameters().please(Arrays.asList(
                "FUNCTION any",
                "(",
                "   param integer",
                ")",
                "RETURN INTEGER;"
        ));
        assertThat(parameters.size(), equalTo(1));
        assertThat(parameters.toList(), equalTo("int param"));
    }

    @Test
    public void parametersSettings() {
        Parameters parameters = new ExtractFunctionParameters().please(Arrays.asList(
                "FUNCTION any(",
                "   field1 INTEGER,",
                "   field2 integer,",
                "   field3 varchar2,",
                "   field4 VARCHAR2",
                ") RETURN INTEGER;"
        ));
        assertThat(parameters.getParametersSettings(), equalTo("" +
                "        statement.setInt(2, field1);\n" +
                "        statement.setInt(3, field2);\n" +
                "        statement.setString(4, field3);\n" +
                "        statement.setString(5, field4);\n" +
                ""));
    }

    @Test
    public void parametersSettingsCanBeEmpty() {
        Parameters parameters = new ExtractFunctionParameters().please(Arrays.asList(
                "FUNCTION any() RETURN INTEGER;"
        ));
        assertThat(parameters.getParametersSettings(), equalTo(""));
    }
}