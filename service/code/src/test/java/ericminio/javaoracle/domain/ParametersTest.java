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
        parameters.add("field number");
        assertThat(parameters.count(), equalTo(1));
        assertThat(parameters.all(), equalTo(Arrays.asList("field number")));
    }

    @Test
    public void keepsGivenSpecificationsOrdered() {
        Parameters parameters = new Parameters();
        parameters.add("field1 number");
        parameters.add("field2 number");
        assertThat(parameters.count(), equalTo(2));
        assertThat(parameters.all(), equalTo(Arrays.asList("field1 number", "field2 number")));
    }

    @Test
    public void exposesParameter() {
        Parameters parameters = new Parameters();
        parameters.add("field number");
        Parameter parameter = parameters.getParameter(0);

        assertThat(parameter.getName(), equalTo("field"));
        assertThat(parameter.getType(), equalTo("number"));
        assertThat(parameter.isOut(), equalTo(false));
    }

    @Test
    public void supportOptionalInKeyword() {
        Parameters parameters = new Parameters();
        parameters.add("field in number");
        Parameter parameter = parameters.getParameter(0);

        assertThat(parameter.getName(), equalTo("field"));
        assertThat(parameter.getType(), equalTo("number"));
        assertThat(parameter.isOut(), equalTo(false));
    }

    @Test
    public void supportOutKeyword() {
        Parameters parameters = new Parameters();
        parameters.add("field out number");
        Parameter parameter = parameters.getParameter(0);

        assertThat(parameter.getName(), equalTo("field"));
        assertThat(parameter.getType(), equalTo("number"));
        assertThat(parameter.isOut(), equalTo(true));
    }
}
