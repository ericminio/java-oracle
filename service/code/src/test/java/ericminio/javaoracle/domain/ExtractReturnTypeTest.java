package ericminio.javaoracle.domain;

import org.junit.Test;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class ExtractReturnTypeTest {

    @Test
    public void works() {
        assertThat(new ExtractReturnType().please(Arrays.asList(
            "FUNCTION get_event_count RETURN NUMBER;"
        )), equalTo("NUMBER"));
    }

    @Test
    public void resistsParenthesis() {
        assertThat(new ExtractReturnType().please(Arrays.asList(
                "FUNCTION get_event_count() RETURN NUMBER;"
        )), equalTo("NUMBER"));
    }

    @Test
    public void resistsParameters() {
        assertThat(new ExtractReturnType().please(Arrays.asList(
                "FUNCTION any(param number) RETURN NUMBER;"
        )), equalTo("NUMBER"));
    }

    @Test
    public void resistsMultiline() {
        assertThat(new ExtractReturnType().please(Arrays.asList(
                "FUNCTION any(",
                "   param number",
                ") RETURN NUMBER;"
        )), equalTo("NUMBER"));
    }

    @Test
    public void resistsOpeningParenthesisOnNewLine() {
        assertThat(new ExtractReturnType().please(Arrays.asList(
                "FUNCTION any",
                "(",
                "   param number",
                ") RETURN NUMBER;"
        )), equalTo("NUMBER"));
    }

    @Test
    public void resistsReturnTypeOnNewLine() {
        assertThat(new ExtractReturnType().please(Arrays.asList(
                "FUNCTION any",
                "(",
                "   param number",
                ")",
                "RETURN NUMBER;"
        )), equalTo("NUMBER"));
    }
}