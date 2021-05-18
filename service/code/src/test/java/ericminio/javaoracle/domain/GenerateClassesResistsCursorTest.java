package ericminio.javaoracle.domain;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class GenerateClassesResistsCursorTest {

    GenerateClasses generateClasses;
    private FileSet fileSet;

    @Before
    public void sut() throws IOException {
        generateClasses = new GenerateClasses();
        Incoming incoming = new Incoming();
        incoming.setPackageSpecification(Arrays.asList(
                "create package any_package as",
                "   type example_cursor is ref cursor;",
                "   function any_function return example_cursor;",
                "end any_package;"
        ));
        fileSet = generateClasses.from(incoming, "examples");
    }

    @Test
    public void doesNotGenerateCursorClass() {
        assertThat(fileSet.size(), equalTo(1));
    }

    @Test
    public void stillGeneratesClassForPackage() {
        assertThat(fileSet.get(0).getFileName(), equalTo("AnyPackage.java"));
    }

    @Test
    public void doesNotGenerateMappingForResulSet() {
        String code = fileSet.get(0).getContent();

        assertThat(code, containsString("package examples;\n\n"));

        assertThat(code, containsString("public class AnyPackage {"));
        assertThat(code, containsString("   private Connection connection;"));
        assertThat(code, containsString("   public AnyPackage(Connection connection) {\n" +
                "        this.connection = connection;\n" +
                "    }"));
        assertThat(code, containsString("   public ResultSet anyFunction()"));
        assertThat(code, containsString("       PreparedStatement statement = connection.prepareStatement(\"select any_package.any_function() from dual\");"));
        assertThat(code, containsString("       return (ResultSet) data;"));
    }
}
