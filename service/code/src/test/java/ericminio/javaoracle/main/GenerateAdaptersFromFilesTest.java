package ericminio.javaoracle.main;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.sql.SQLException;

import static ericminio.javaoracle.support.FileUtils.exactContentOf;
import static ericminio.javaoracle.support.FileUtils.safeDelete;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class GenerateAdaptersFromFilesTest {

    private GenerateAdaptersFromFiles generateAdaptersFromFiles;

    @Before
    public void generateAdapters() throws SQLException, IOException {
        safeDelete("target/AnyPackage.java");
        safeDelete("target/ExampleArrayType.java");
        safeDelete("target/ExampleAnyType.java");
        generateAdaptersFromFiles = new GenerateAdaptersFromFiles();
        generateAdaptersFromFiles.fromFiles(
                "src/test/resources/create-package.sql",
                "src/test/resources/create-types.sql",
                "examples",
                "target");
    }

    @Test
    public void createsPackageFile() throws Exception {
        String code = exactContentOf("target/AnyPackage.java");

        assertThat(code, containsString("package examples;\n\n"));
        assertThat(code, containsString("public class AnyPackage {"));
        assertThat(code, containsString("   private Connection connection;"));
        assertThat(code, containsString("   public AnyPackage(Connection connection) throws SQLException {"));
        assertThat(code, containsString("       connection.getTypeMap().put(ExampleAnyType.NAME, ExampleAnyType.class);"));

        assertThat(code, containsString("   public ExampleArrayType anyFunction()"));
        assertThat(code, containsString("       CallableStatement statement = connection.prepareCall(\"{ ? = call any_package.any_function() }\");"));
        assertThat(code, containsString("       return ExampleArrayType.with((Object[]) ((java.sql.Array) data).getArray());"));
    }

    @Test
    public void createsArrayTypeFile() throws Exception {
        String code = exactContentOf("target/ExampleArrayType.java");

        assertThat(code, containsString("package examples;\n\n"));
        assertThat(code, containsString("public class ExampleArrayType {"));
    }

    @Test
    public void createsRecordTypeFile() throws Exception {
        String code = exactContentOf("target/ExampleAnyType.java");

        assertThat(code, containsString("package examples;\n\n"));
        assertThat(code, containsString("public class ExampleAnyType implements SQLData {"));
        assertThat(code, containsString("    public static final String NAME = \"EXAMPLE_ANY_TYPE\";"));
    }

    @Test
    public void logsPackageGeneration() throws SQLException, IOException {
        assertThat(generateAdaptersFromFiles.getLog(), containsString("Generating class for package"));
        assertThat(generateAdaptersFromFiles.getLog(), containsString("-> examples.AnyPackage"));
    }

    @Test
    public void logsTypeGeneration() throws SQLException, IOException {
        assertThat(generateAdaptersFromFiles.getLog(), containsString("Generating classes for types"));
        assertThat(generateAdaptersFromFiles.getLog(), containsString("-> generating class for type example_array_type"));
        assertThat(generateAdaptersFromFiles.getLog(), containsString("-> generating class for type example_any_type"));
    }

    @Test
    public void logsToConsole() {
        GenerateAdaptersFromFiles generateAdaptersFromFiles = new GenerateAdaptersFromFiles();
        assertThat(generateAdaptersFromFiles.getLogSink().logsToConsole(), equalTo(true));
    }
}
