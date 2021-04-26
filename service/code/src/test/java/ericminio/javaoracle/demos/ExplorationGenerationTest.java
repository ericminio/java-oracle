package ericminio.javaoracle.demos;

import ericminio.javaoracle.Generator;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class ExplorationGenerationTest {

    @Test
    public void works() throws IOException {
        String actual = generate(Arrays.asList(
            "PACKAGE EXPLORATION\n",
            "AS\n",
            "   FUNCTION get_event_count RETURN INTEGER;\n",
            "END EXPLORATION;"
        ));
        
        assertThat(actual, equalTo(contentOf("Exploration.java")));
    }

    private String contentOf(String filename) throws IOException {
        return Files.readAllLines(Paths.get(
            "./src/main/java/ericminio/javaoracle/demos/" + filename ))
            .stream()
            .filter(line -> !line.trim().startsWith("package"))
            .collect(Collectors.joining("\n"));
    }

    private String generate(List<String> specification) throws IOException {
        return new Generator().generate(specification);
    }
}