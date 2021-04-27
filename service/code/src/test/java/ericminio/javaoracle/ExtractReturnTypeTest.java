package ericminio.javaoracle;

import org.junit.Test;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class ExtractReturnTypeTest {

    @Test
    public void works() {
        assertThat(new ExtractReturnType().please(Arrays.asList(
            "FUNCTION get_event_count RETURN INTEGER;"
        )), equalTo("INTEGER"));
    }

    @Test
    public void resistsParenthesis() {
        assertThat(new ExtractReturnType().please(Arrays.asList(
                "FUNCTION get_event_count() RETURN INTEGER;"
        )), equalTo("INTEGER"));
    }

    @Test
    public void resistsParameters() {
        assertThat(new ExtractReturnType().please(Arrays.asList(
                "FUNCTION any(param integer) RETURN INTEGER;"
        )), equalTo("INTEGER"));
    }

    @Test
    public void resistsMultiline() {
        assertThat(new ExtractReturnType().please(Arrays.asList(
                "FUNCTION any(",
                "   param integer",
                ") RETURN INTEGER;"
        )), equalTo("INTEGER"));
    }

    @Test
    public void resistsOpeningParenthesisOnNewLine() {
        assertThat(new ExtractReturnType().please(Arrays.asList(
                "FUNCTION any",
                "(",
                "   param integer",
                ") RETURN INTEGER;"
        )), equalTo("INTEGER"));
    }

    @Test
    public void resistsReturnTypeOnNewLine() {
        assertThat(new ExtractReturnType().please(Arrays.asList(
                "FUNCTION any",
                "(",
                "   param integer",
                ")",
                "RETURN INTEGER;"
        )), equalTo("INTEGER"));
    }
}