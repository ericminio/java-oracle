package ericminio.javaoracle.domain;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class GenerateClassesTest {

    GenerateClasses generateClasses;
    private FileSet fileSet;

    @Before
    public void sut() throws IOException {
        generateClasses = new GenerateClasses();
        Incoming incoming = new Incoming();
        incoming.setPackageSpecification(Arrays.asList(
                "create package any_package as",
                "   function any_function return example_array_type;",
                "end any_package;"
        ));
        incoming.setTypeSpecifications(Arrays.asList(
                Arrays.asList("create or replace type example_any_type as object(value number(15,4))"),
                Arrays.asList("create or replace type example_array_type as table of example_any_type")
        ));
        fileSet = generateClasses.from(incoming, "examples");
    }

    @Test
    public void expectedFileSetSize() {
        assertThat(fileSet.size(), equalTo(3));
    }

    @Test
    public void expectedFileSetOrder() throws IOException {
        assertThat(fileSet.get(0).getFileName(), equalTo("AnyPackage.java"));
        assertThat(fileSet.get(1).getFileName(), equalTo("ExampleAnyType.java"));
        assertThat(fileSet.get(2).getFileName(), equalTo("ExampleArrayType.java"));
    }

    @Test
    public void packageCode() {
        String code = fileSet.get(0).getContent();

        assertThat(code, containsString("package examples;\n\n"));

        assertThat(code, containsString("public class AnyPackage {"));
        assertThat(code, containsString("   private Connection connection;"));
        assertThat(code, containsString("   public AnyPackage(Connection connection) throws SQLException {"));
        assertThat(code, containsString("       connection.getTypeMap().put(ExampleAnyType.NAME, ExampleAnyType.class);"));
        assertThat(code, containsString("   public ExampleArrayType anyFunction()"));
        assertThat(code, containsString("       PreparedStatement statement = connection.prepareStatement(\"select any_package.any_function() from dual\");"));
        assertThat(code, containsString("       return ExampleArrayType.with((Object[]) ((java.sql.Array) data).getArray());"));
    }

    @Test
    public void recordTypeCode() throws Exception {
        String code = fileSet.get(1).getContent();

        Assert.assertThat(code, containsString("package examples;\n\n"));
        Assert.assertThat(code, containsString("public class ExampleAnyType implements SQLData {"));
        Assert.assertThat(code, containsString("    public static final String NAME = \"EXAMPLE_ANY_TYPE\";"));
    }

    @Test
    public void arrayTypeCode() throws Exception {
        String code = fileSet.get(2).getContent();

        Assert.assertThat(code, containsString("package examples;\n\n"));
        Assert.assertThat(code, containsString("public class ExampleArrayType {"));
    }
}
