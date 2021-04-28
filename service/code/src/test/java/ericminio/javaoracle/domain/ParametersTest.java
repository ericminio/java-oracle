package ericminio.javaoracle.domain;

import org.junit.Test;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class ParametersTest {

    @Test
    public void emptyByDefault() {
        Parameters parameters = new Parameters();
        assertThat(parameters.count(), equalTo(0));
        assertThat(parameters.all(), equalTo(Arrays.asList(new String[]{})));
    }

    @Test
    public void keepsGivenSpecification() {
        Parameters parameters = new Parameters();
        parameters.add("field integer");
        assertThat(parameters.count(), equalTo(1));
        assertThat(parameters.all(), equalTo(Arrays.asList("field integer")));
    }

    @Test
    public void keepsGivenSpecificationsOrdered() {
        Parameters parameters = new Parameters();
        parameters.add("field1 integer");
        parameters.add("field2 integer");
        assertThat(parameters.count(), equalTo(2));
        assertThat(parameters.all(), equalTo(Arrays.asList("field1 integer", "field2 integer")));
    }
}
