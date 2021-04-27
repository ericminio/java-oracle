package ericminio.javaoracle.domain;

import ericminio.javaoracle.domain.ExtractFunctionSpecifications;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class ExtractFunctionSpecificationsTest {

    @Test
    public void worksWithSingleLineDefinition() {
        List<List<String>> actual = new ExtractFunctionSpecifications().please(Arrays.asList(
            "PACKAGE beautiful\n",
            "AS\n",
            "   FUNCTION function_1 RETURN INTEGER;\n",
            "   FUNCTION function_2 RETURN INTEGER;\n",
            "END beautiful;"
        ));
        List<List<String>> expected = Arrays.asList(
                Arrays.asList("FUNCTION function_1 RETURN INTEGER;"),
                Arrays.asList("FUNCTION function_2 RETURN INTEGER;")
        );
        assertThat(actual, equalTo(expected));
    }

    @Test
    public void worksWithTwoLinesDefinition() {
        List<List<String>> actual = new ExtractFunctionSpecifications().please(Arrays.asList(
                "PACKAGE beautiful\n",
                "AS\n",
                "   FUNCTION function_1 RETURN INTEGER;\n",
                "   FUNCTION function_2\n",
                "       RETURN INTEGER;\n",
                "END beautiful;"
        ));
        List<List<String>> expected = Arrays.asList(
                Arrays.asList("FUNCTION function_1 RETURN INTEGER;"),
                Arrays.asList("FUNCTION function_2", "RETURN INTEGER;")
        );
        assertThat(actual, equalTo(expected));
    }

    @Test
    public void worksWithSeveralLinesDefinition() {
        List<List<String>> actual = new ExtractFunctionSpecifications().please(Arrays.asList(
                "PACKAGE beautiful\n",
                "AS\n",
                "   FUNCTION function_1 RETURN INTEGER;\n",
                "   FUNCTION function_2(\n",
                "           input_1 INTEGER,\n",
                "           input_2 INTEGER\n",
                "       )\n",
                "       RETURN INTEGER;\n",
                "END beautiful;"
        ));
        List<List<String>> expected = Arrays.asList(
                Arrays.asList("FUNCTION function_1 RETURN INTEGER;"),
                Arrays.asList("FUNCTION function_2(", "input_1 INTEGER,", "input_2 INTEGER", ")", "RETURN INTEGER;")
        );
        assertThat(actual, equalTo(expected));
    }
}