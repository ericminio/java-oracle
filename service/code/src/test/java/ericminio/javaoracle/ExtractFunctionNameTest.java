package ericminio.javaoracle;

import org.junit.Test;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class ExtractFunctionNameTest {

    @Test
    public void works() {
        assertThat(new ExtractFunctionName().please(Arrays.asList(
            "PACKAGE beautiful\n",
            "AS\n",
            "   FUNCTION get_event_count RETURN INTEGER;\n",
            "END beautiful;"
        )), equalTo("get_event_count"));
    }
}