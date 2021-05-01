package ericminio.javaoracle;

import ericminio.javaoracle.support.DatabaseTest;
import org.junit.Before;
import org.junit.Test;

import static ericminio.javaoracle.data.Query.with;
import static ericminio.javaoracle.support.FileUtils.exactContentOf;
import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;

public class GenerateTypeAdaptersTest extends DatabaseTest {

    @Before
    public void createTypes() {
        with(connection).execute("create or replace type example_types_one as object(value number(15,4));");
        with(connection).execute("create or replace type example_types_two as object(value varchar2(20));");
        with(connection).execute("create or replace type example_types_three as object(value date);");
    }

    @Test
    public void createsExpectedFiles() throws Exception {
        new GenerateTypeAdapters().go("example_types_", "examples", "target");

        assertThat(exactContentOf("target/ExampleTypesOne.java"), containsString("public class ExampleTypesOne implements SQLData {"));
        assertThat(exactContentOf("target/ExampleTypesTwo.java"), containsString("public class ExampleTypesTwo implements SQLData {"));
        assertThat(exactContentOf("target/ExampleTypesThree.java"), containsString("public class ExampleTypesThree implements SQLData {"));
    }
}
