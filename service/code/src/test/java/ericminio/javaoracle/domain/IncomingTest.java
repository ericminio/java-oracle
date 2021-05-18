package ericminio.javaoracle.domain;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

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
}
