package ericminio.javaoracle;

import ericminio.javaoracle.support.DatabaseTest;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static ericminio.javaoracle.support.FileUtils.exactContentOf;
import static org.hamcrest.CoreMatchers.containsString;
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
}
