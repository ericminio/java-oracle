package ericminio.javaoracle;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.util.Arrays;

import org.junit.Test;

public class ExtractFunctionNameTest {

    @Test
    public void works() {
        assertThat(new ExtractFunctionName().please(Arrays.asList(
            "PACKAGE beautiful\n",
            "AS\n",
            "   FUNCTION get_event_count RETURN NUMBER;\n",
            "END beautiful;"
        )), equalTo("get_event_count"));
    }
}