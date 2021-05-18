package ericminio.javaoracle.domain;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class IncomingTest {

    Incoming incoming;

    @Before
    public void sut() {
        incoming = new Incoming();
    }

    @Test
    public void extractTypeNamesFromTypeSpecifications() {
        incoming.setTypeSpecifications(Arrays.asList(
                Arrays.asList("create or replace type example_any_type as object(value number(15,4))"),
                Arrays.asList("create or replace type example_array_type as table of example_any_type")
        ));

        assertThat(incoming.getTypeNames().size(), equalTo(2));
        assertThat(incoming.getTypeNames().get(0), equalTo("example_any_type"));
        assertThat(incoming.getTypeNames().get(1), equalTo("example_array_type"));
    }

    @Test
    public void extractTypeDefinedInPackage() {
        incoming.setPackageSpecification(Arrays.asList(
                "create package any_package as",
                "   type example_cursor is ref cursor;",
                "   function any_function return example_cursor;",
                "end any_package;"
        ));
        assertThat(incoming.getTypeSpecifications().size(), equalTo(1));
        assertThat(incoming.getTypeSpecifications().get(0).size(), equalTo(1));
        assertThat(incoming.getTypeSpecifications().get(0).get(0), equalTo("type example_cursor is ref cursor;"));
    }

    @Test
    public void extendsExistingTypeSpecifications() {
        incoming.setTypeSpecifications(Arrays.asList(
                Arrays.asList("create or replace type example_any_type as object(value number(15,4))"),
                Arrays.asList("create or replace type example_array_type as table of example_any_type")
        ));

        incoming.setPackageSpecification(Arrays.asList(
                "create package any_package as",
                "   type example_cursor is ref cursor;",
                "   function any_function return example_cursor;",
                "end any_package;"
        ));
        assertThat(incoming.getTypeSpecifications().size(), equalTo(3));
        assertThat(incoming.getTypeSpecifications().get(0).get(0), containsString("example_any_type"));
        assertThat(incoming.getTypeSpecifications().get(1).get(0), containsString("example_array_type"));
        assertThat(incoming.getTypeSpecifications().get(2).get(0), containsString("example_cursor"));
    }

    @Test
    public void willStayInTypeSpecifications() {
        incoming.setPackageSpecification(Arrays.asList(
                "create package any_package as",
                "   type example_cursor is ref cursor;",
                "   function any_function return example_cursor;",
                "end any_package;"
        ));
        incoming.setTypeSpecifications(Arrays.asList(
                Arrays.asList("create or replace type example_any_type as object(value number(15,4))"),
                Arrays.asList("create or replace type example_array_type as table of example_any_type")
        ));
        assertThat(incoming.getTypeSpecifications().size(), equalTo(3));
    }
}
