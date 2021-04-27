package ericminio.javaoracle.domain;

import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class GenerateClassCodeTest {

    @Test
    public void disclosesPackageName() throws IOException {
        GenerateClassCode generateClassCode = new GenerateClassCode();
        generateClassCode.please(Arrays.asList(
                "PACKAGE EXPLORATION\n",
                "AS\n",
                "   FUNCTION get_event_count RETURN INTEGER;\n",
                "END EXPLORATION;"
        ));
        assertThat(generateClassCode.getPackageName(), equalTo("exploration"));
    }
}
