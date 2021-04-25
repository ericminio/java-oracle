package ericminio.javaoracle;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.util.Arrays;

import org.junit.Test;

public class ExtractClassNameTest {

    @Test
    public void works() {
        assertThat(new ExtractClassName().please(Arrays.asList(
            "PACKAGE EXPLORATION\n",
            "AS\n",
            "   FUNCTION get_event_count RETURN NUMBER;\n",
            "END EXPLORATION;"
        )), equalTo("Exploration"));
    }

    @Test
    public void resistsLowerCase() {
        assertThat(new ExtractClassName().please(Arrays.asList(
            "PACKAGE beautiful\n",
            "AS\n",
            "END beautiful;"
        )), equalTo("Beautiful"));
    }
}