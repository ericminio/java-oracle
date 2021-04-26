package ericminio.javaoracle.demos;

import ericminio.javaoracle.Generator;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

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
        List<String> lines = Files.readAllLines(Paths.get(
            "./src/main/java/ericminio/javaoracle/demos/" + filename ), StandardCharsets.UTF_8);
        lines.remove(0);

        String content = "";
        for (int i=0; i<lines.size(); i++) {
            String line = lines.get(i);
            if (!line.trim().startsWith("package")) {
                content += line;
                if (i != lines.size() - 1) {
                    content += "\n";
                }
            }
        }
        return content;
    }

    private String generate(List<String> specification) throws IOException {
        return new Generator().generate(specification);
    }
}