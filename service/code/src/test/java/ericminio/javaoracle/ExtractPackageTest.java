package ericminio.javaoracle;

import ericminio.javaoracle.support.Database;
import org.junit.Before;
import org.junit.Test;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static ericminio.javaoracle.support.Query.with;
import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;

public class ExtractPackageTest {

    @Before
    public void createPackage() throws Exception {
        String creation = "CREATE OR REPLACE PACKAGE example\n" +
                "AS\n" +
                "\n" +
                "    FUNCTION hello(\n" +
                "        value varchar2\n" +
                "    ) RETURN integer;\n" +
                "\n" +
                "    FUNCTION world(\n" +
                "        value varchar2\n" +
                "    ) RETURN integer;\n" +
                "\n" +
                "END example";
        with(new Database().connection()).execute(creation);
    }

    @Test
    public void createsExpectedFile() throws Exception {
        ExtractPackage extractPackage = new ExtractPackage();
        extractPackage.go("example", "examples", "../demos");
        Path path = Paths.get("../demos/Example.java");
        List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);
        String code = "";
        for (int i=0; i<lines.size(); i++) {
            code += lines.get(i) + "\n";
        }

        assertThat(code, containsString("public class Example {"));
        assertThat(code, containsString("public int hello(String value)"));
        assertThat(code, containsString("public int world(String value)"));
    }
}
