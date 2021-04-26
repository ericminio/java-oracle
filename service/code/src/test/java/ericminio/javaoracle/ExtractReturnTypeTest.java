package ericminio.javaoracle;

import org.junit.Test;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class ExtractReturnTypeTest {

    @Test
    public void worksWithSingleLineDefinition() {
        assertThat(new ExtractReturnType().please(Arrays.asList(
            "FUNCTION get_event_count RETURN INTEGER;"
        )), equalTo("INTEGER"));
    }
}