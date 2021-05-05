package ericminio.javaoracle;

import ericminio.javaoracle.support.DatabaseTest;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.sql.SQLException;

import static ericminio.javaoracle.support.FileUtils.exactContentOf;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class GenerateAdaptersTest extends DatabaseTest {

    @Before
    public void initDatabase() throws IOException {
        executeFromResource("create.sql");
    }

    @Test
    public void createsPackageFile() throws Exception {
        new GenerateAdapters().go("any_package", "example_", "examples", "target");
        String code = exactContentOf("target/AnyPackage.java");

        assertThat(code, containsString("public class AnyPackage {"));
        assertThat(code, containsString("   private Connection connection;"));
        assertThat(code, containsString("   public AnyPackage(Connection connection) throws SQLException {"));
        assertThat(code, containsString("       connection.getTypeMap().put(ExampleAnyType.NAME, ExampleAnyType.class);"));

        assertThat(code, containsString("   public ExampleArrayType anyFunction()"));
        assertThat(code, containsString("       return ExampleArrayType.with((Object[]) resultSet.getArray(1).getArray());"));
    }

    @Test
    public void createsArrayTypeFile() throws Exception {
        new GenerateAdapters().go("any_package", "example_", "examples", "target");
        String code = exactContentOf("target/ExampleArrayType.java");

        assertThat(code, containsString("public class ExampleArrayType {"));
    }

    @Test
    public void createsRecordTypeFile() throws Exception {
        new GenerateAdapters().go("any_package", "example_", "examples", "target");
        String code = exactContentOf("target/ExampleAnyType.java");

        assertThat(code, containsString("public class ExampleAnyType implements SQLData {"));
    }

    @Test
    public void logsPackageGeneration() throws SQLException, IOException {
        GenerateAdapters generator = new GenerateAdapters();
        generator.go("any_package", "example_", "examples", "target");

        assertThat(generator.getLog(), containsString("INFO: Generating class for package"));
        assertThat(generator.getLog(), containsString("INFO: -> examples.AnyPackage"));
    }

    @Test
    public void logsTypeGeneration() throws SQLException, IOException {
        GenerateAdapters generator = new GenerateAdapters();
        generator.go("any_package", "example_", "examples", "target");

        assertThat(generator.getLog(), containsString("INFO: Generating classes for types"));
        assertThat(generator.getLog(), containsString("INFO: -> examples.ExampleArrayType"));
        assertThat(generator.getLog(), containsString("INFO: -> examples.ExampleAnyType"));
    }

    @Test
    public void logsToConsole() {
        GenerateAdapters generator = new GenerateAdapters();
        assertThat(generator.getLogSink().logsToConsole(), equalTo(true));
    }
}
