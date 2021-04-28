package ericminio.javaoracle.domain;

import org.junit.Test;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class ExtractParametersTest {

    @Test
    public void withoutParameter() {
        Parameters parameters = new ExtractParameters().please(Arrays.asList(
            "FUNCTION get_event_count RETURN INTEGER;"
        ));
        assertThat(parameters.count(), equalTo(0));
        assertThat(parameters.all(), equalTo(Arrays.asList(new String[]{})));
    }

    @Test
    public void withEmptyParameterList() {
        Parameters parameters = new ExtractParameters().please(Arrays.asList(
                "FUNCTION get_event_count() RETURN INTEGER;"
        ));
        assertThat(parameters.count(), equalTo(0));
        assertThat(parameters.all(), equalTo(Arrays.asList(new String[]{})));
    }

    @Test
    public void withOneIntegerParameter() {
        Parameters parameters = new ExtractParameters().please(Arrays.asList(
                "FUNCTION any(param integer) RETURN INTEGER;"
        ));
        assertThat(parameters.count(), equalTo(1));
        assertThat(parameters.all(), equalTo(Arrays.asList("param integer")));
    }

    @Test
    public void withOneVarcharParameter() {
        Parameters parameters = new ExtractParameters().please(Arrays.asList(
                "FUNCTION any(param varchar2) RETURN INTEGER;"
        ));
        assertThat(parameters.count(), equalTo(1));
        assertThat(parameters.all(), equalTo(Arrays.asList("param varchar2")));
    }

    @Test
    public void withOneVarcharParameterIncludingSize() {
        Parameters parameters = new ExtractParameters().please(Arrays.asList(
                "FUNCTION any(param varchar2(10)) RETURN INTEGER;"
        ));
        assertThat(parameters.count(), equalTo(1));
        assertThat(parameters.all(), equalTo(Arrays.asList("param varchar2(10)")));
    }

    @Test
    public void withTwoParameters() {
        Parameters parameters = new ExtractParameters().please(Arrays.asList(
                "FUNCTION any(field1 integer, field2 varchar2) RETURN INTEGER;"
        ));
        assertThat(parameters.count(), equalTo(2));
        assertThat(parameters.all(), equalTo(Arrays.asList("field1 integer", "field2 varchar2")));
    }

    @Test
    public void resistsMultiline() {
        Parameters parameters = new ExtractParameters().please(Arrays.asList(
                "FUNCTION any(",
                "   field1 INTEGER,",
                "   field2 integer,",
                "   field3 varchar2,",
                "   field4 VARCHAR2",
                ") RETURN INTEGER;"
        ));
        assertThat(parameters.count(), equalTo(4));
        assertThat(parameters.all(), equalTo(Arrays.asList(
                "field1 INTEGER", "field2 integer", "field3 varchar2", "field4 VARCHAR2"
        )));
    }

    @Test
    public void canBeUsedToParseType() {
        Parameters parameters = new ExtractParameters().please(Arrays.asList(
                "TYPE any_type as object (",
                "   field1 INTEGER,",
                "   field2 integer,",
                "   field3 varchar2(10),",
                "   field4 VARCHAR2",
                ") RETURN INTEGER;"
        ));
        assertThat(parameters.count(), equalTo(4));
        assertThat(parameters.all(), equalTo(Arrays.asList(
                "field1 INTEGER", "field2 integer", "field3 varchar2(10)", "field4 VARCHAR2"
        )));
    }

    @Test
    public void resistsOpeningParenthesisOnNewLine() {
        Parameters parameters = new ExtractParameters().please(Arrays.asList(
                "FUNCTION any",
                "(",
                "   param integer",
                ") RETURN INTEGER;"
        ));
        assertThat(parameters.count(), equalTo(1));
        assertThat(parameters.all(), equalTo(Arrays.asList("param integer")));
    }

    @Test
    public void resistsReturnTypeOnNewLine() {
        Parameters parameters = new ExtractParameters().please(Arrays.asList(
                "FUNCTION any",
                "(",
                "   param integer",
                ")",
                "RETURN INTEGER;"
        ));
        assertThat(parameters.count(), equalTo(1));
        assertThat(parameters.all(), equalTo(Arrays.asList("param integer")));
    }
}