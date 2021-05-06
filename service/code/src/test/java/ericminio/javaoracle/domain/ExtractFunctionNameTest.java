package ericminio.javaoracle.domain;

import org.junit.Test;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class ExtractFunctionNameTest {

    @Test
    public void works() {
        assertThat(new ExtractFunctionName().please(Arrays.asList(
            "FUNCTION get_event_count RETURN INTEGER;"
        )), equalTo("get_event_count"));
    }

    @Test
    public void resistsParenthesis() {
        assertThat(new ExtractFunctionName().please(Arrays.asList(
                "FUNCTION get_event_count() RETURN INTEGER;"
        )), equalTo("get_event_count"));
    }

    @Test
    public void resistsParameters() {
        assertThat(new ExtractFunctionName().please(Arrays.asList(
                "FUNCTION any(param integer) RETURN INTEGER;"
        )), equalTo("any"));
    }

    @Test
    public void resistsMultiline() {
        assertThat(new ExtractFunctionName().please(Arrays.asList(
                "FUNCTION any(",
                "   param integer",
                ") RETURN INTEGER;"
        )), equalTo("any"));
    }

    @Test
    public void resistsOpeningParenthesisOnNewLine() {
        assertThat(new ExtractFunctionName().please(Arrays.asList(
                "FUNCTION any",
                "(",
                "   param integer",
                ") RETURN INTEGER;"
        )), equalTo("any"));
    }

    @Test
    public void resistsReturnTypeOnNewLine() {
        assertThat(new ExtractFunctionName().please(Arrays.asList(
                "FUNCTION any",
                "(",
                "   param integer",
                ")",
                "RETURN INTEGER;"
        )), equalTo("any"));
    }

    @Test
    public void resistsLowercase() {
        assertThat(new ExtractFunctionName().please(Arrays.asList(
                "function get_event_count RETURN INTEGER;"
        )), equalTo("get_event_count"));
    }

    @Test
    public void resistsNameContainingFunction() {
        assertThat(new ExtractFunctionName().please(Arrays.asList(
                "function any_function RETURN INTEGER;"
        )), equalTo("any_function"));
    }

    @Test
    public void ignoresComment() {
        assertThat(new ExtractFunctionName().please(Arrays.asList(
                "function any_function\n",
                "--ignore me",
                "RETURN INTEGER;"
        )), equalTo("any_function"));
    }

    @Test
    public void ignoresCommentAfterName() {
        assertThat(new ExtractFunctionName().please(Arrays.asList(
                "function any_function --ignore me\n",
                "RETURN INTEGER;"
        )), equalTo("any_function"));
    }

    @Test
    public void ignoresCommentImmediatelyAfterName() {
        assertThat(new ExtractFunctionName().please(Arrays.asList(
                "function any_function--ignore me\n",
                "RETURN INTEGER;"
        )), equalTo("any_function"));
    }

    @Test
    public void resistsFunctionNameOnNewLine() {
        assertThat(new ExtractFunctionName().please(Arrays.asList(
                "function",
                "any_function",
                "RETURN INTEGER;"
        )), equalTo("any_function"));
    }
}