package ericminio.javaoracle.domain;

import org.junit.Test;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class ExtractParametersTest {

    @Test
    public void withoutParameter() {
        Parameters parameters = new ExtractParameters().please(Arrays.asList(
            "FUNCTION get_event_count RETURN number;"
        ));
        assertThat(parameters.count(), equalTo(0));
        assertThat(parameters.all(), equalTo(Arrays.asList(new String[]{})));
    }

    @Test
    public void withEmptyParameterList() {
        Parameters parameters = new ExtractParameters().please(Arrays.asList(
                "FUNCTION get_event_count() RETURN number;"
        ));
        assertThat(parameters.count(), equalTo(0));
        assertThat(parameters.all(), equalTo(Arrays.asList(new String[]{})));
    }

    @Test
    public void withOneNumberParameter() {
        Parameters parameters = new ExtractParameters().please(Arrays.asList(
                "FUNCTION any(param number) RETURN number;"
        ));
        assertThat(parameters.count(), equalTo(1));
        assertThat(parameters.all(), equalTo(Arrays.asList("param number")));
    }

    @Test
    public void withOneNumberParameterIncludingDetails() {
        Parameters parameters = new ExtractParameters().please(Arrays.asList(
                "FUNCTION any(param number(15,4)) RETURN number;"
        ));
        assertThat(parameters.count(), equalTo(1));
        assertThat(parameters.all(), equalTo(Arrays.asList("param number(15,4)")));
    }

    @Test
    public void withOneNumberParameterIncludingInKeyword() {
        Parameters parameters = new ExtractParameters().please(Arrays.asList(
                "FUNCTION any(param IN number(15,4)) RETURN number;"
        ));
        assertThat(parameters.count(), equalTo(1));
        assertThat(parameters.all(), equalTo(Arrays.asList("param IN number(15,4)")));
    }

    @Test
    public void withOneNumberParameterIncludingDetailsWithSpace() {
        Parameters parameters = new ExtractParameters().please(Arrays.asList(
                "FUNCTION any(param number(15, 4)) RETURN number;"
        ));
        assertThat(parameters.count(), equalTo(1));
        assertThat(parameters.all(), equalTo(Arrays.asList("param number(15, 4)")));
    }

    @Test
    public void withOneVarcharParameter() {
        Parameters parameters = new ExtractParameters().please(Arrays.asList(
                "FUNCTION any(param varchar2) RETURN number;"
        ));
        assertThat(parameters.all(), equalTo(Arrays.asList("param varchar2")));
    }

    @Test
    public void withOneVarcharParameterIncludingSize() {
        Parameters parameters = new ExtractParameters().please(Arrays.asList(
                "FUNCTION any(param varchar2(10)) RETURN number;"
        ));
        assertThat(parameters.all(), equalTo(Arrays.asList("param varchar2(10)")));
    }

    @Test
    public void withTwoParameters() {
        Parameters parameters = new ExtractParameters().please(Arrays.asList(
                "FUNCTION any(field1 number, field2 varchar2) RETURN number;"
        ));
        assertThat(parameters.count(), equalTo(2));
        assertThat(parameters.all(), equalTo(Arrays.asList("field1 number", "field2 varchar2")));
    }

    @Test
    public void resistsMultiline() {
        Parameters parameters = new ExtractParameters().please(Arrays.asList(
                "FUNCTION any(",
                "   field1 number(15, 4),",
                "   field2 NUMBER,",
                "   field3 varchar2,",
                "   field4 VARCHAR2",
                ") RETURN number;"
        ));
        assertThat(parameters.count(), equalTo(4));
        assertThat(parameters.all(), equalTo(Arrays.asList(
                "field1 number(15, 4)", "field2 NUMBER", "field3 varchar2", "field4 VARCHAR2"
        )));
    }

    @Test
    public void resistsOpeningParenthesisOnNewLine() {
        Parameters parameters = new ExtractParameters().please(Arrays.asList(
                "FUNCTION any",
                "(",
                "   param number",
                ") RETURN number;"
        ));
        assertThat(parameters.all(), equalTo(Arrays.asList("param number")));
    }

    @Test
    public void resistsReturnTypeOnNewLine() {
        Parameters parameters = new ExtractParameters().please(Arrays.asList(
                "FUNCTION any",
                "(",
                "   param number",
                ")",
                "RETURN number;"
        ));
        assertThat(parameters.all(), equalTo(Arrays.asList("param number")));
    }

    @Test
    public void resistsFirstParameterOnOpeningParenthesisLine() {
        Parameters parameters = new ExtractParameters().please(Arrays.asList(
                "FUNCTION any",
                "(param1 number,",
                "   param2 number",
                ")",
                "RETURN number;"
        ));
        assertThat(parameters.all(), equalTo(Arrays.asList("param1 number", "param2 number")));
    }

    @Test
    public void ignoresComment() {
        Parameters parameters = new ExtractParameters().please(Arrays.asList(
                "FUNCTION any",
                "(",
                "   -- ignore me",
                "   param number",
                ")",
                "RETURN number;"
        ));
        assertThat(parameters.all(), equalTo(Arrays.asList("param number")));
    }

    @Test
    public void ignoresCommentAfterField() {
        Parameters parameters = new ExtractParameters().please(Arrays.asList(
                "FUNCTION any",
                "(",
                "   param number -- ignore me",
                ")",
                "RETURN number;"
        ));
        assertThat(parameters.all(), equalTo(Arrays.asList("param number")));
    }

    @Test
    public void ignoresCommentImmediatelyAfterField() {
        Parameters parameters = new ExtractParameters().please(Arrays.asList(
                "FUNCTION any",
                "(",
                "   param number-- ignore me",
                ")",
                "RETURN number;"
        ));
        assertThat(parameters.all(), equalTo(Arrays.asList("param number")));
    }

    @Test
    public void canBeUsedToParseType() {
        Parameters parameters = new ExtractParameters().please(Arrays.asList(
                "TYPE any_type as object (",
                "   field1 NUMBER,",
                "   field2 number,",
                "   field3 varchar2(10),",
                "   field4 VARCHAR2",
                ") RETURN number;"
        ));
        assertThat(parameters.all(), equalTo(Arrays.asList(
                "field1 NUMBER", "field2 number", "field3 varchar2(10)", "field4 VARCHAR2"
        )));
    }

    @Test
    public void typeParsingResistsVarray() {
        Parameters parameters = new ExtractParameters().please(Arrays.asList(
                "TYPE any_type as varray(15) of number;"
        ));
        assertThat(parameters.count(), equalTo(0));
        assertThat(parameters.all(), equalTo(Arrays.asList(new String[]{})));
    }

    @Test
    public void typeParsingResistsUpperCaseVarray() {
        Parameters parameters = new ExtractParameters().please(Arrays.asList(
                "TYPE any_type as VARRAY(15) of number;"
        ));
        assertThat(parameters.count(), equalTo(0));
        assertThat(parameters.all(), equalTo(Arrays.asList(new String[]{})));
    }
}