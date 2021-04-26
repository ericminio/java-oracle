package ericminio.javaoracle;

import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class GeneratorTest {

    @Test
    public void disclosesPackageName() throws IOException {
        Generator generator = new Generator();
        generator.generate(Arrays.asList(
                "PACKAGE EXPLORATION\n",
                "AS\n",
                "   FUNCTION get_event_count RETURN INTEGER;\n",
                "END EXPLORATION;"
        ));
        assertThat(generator.getPackageName(), equalTo("exploration"));
    }
}
