package ericminio.javaoracle.domain;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class ParameterTest {

    @Test
    public void exposesParts() {
        Parameter parameter = new Parameter("field number");

        assertThat(parameter.getName(), equalTo("field"));
        assertThat(parameter.getType(), equalTo("number"));
        assertThat(parameter.isOut(), equalTo(false));
    }

    @Test
    public void supportsIn() {
        Parameter parameter = new Parameter("field in number");

        assertThat(parameter.getName(), equalTo("field"));
        assertThat(parameter.getType(), equalTo("number"));
        assertThat(parameter.isOut(), equalTo(false));
    }

    @Test
    public void supportsOut() {
        Parameter parameter = new Parameter("field out number");

        assertThat(parameter.getName(), equalTo("field"));
        assertThat(parameter.getType(), equalTo("number"));
        assertThat(parameter.isOut(), equalTo(true));
    }

    @Test
    public void lowerCases() {
        Parameter parameter = new Parameter("FIELD OUT NUMBER");

        assertThat(parameter.getName(), equalTo("field"));
        assertThat(parameter.getType(), equalTo("number"));
        assertThat(parameter.isOut(), equalTo(true));
    }

    @Test
    public void ignoresExtraSpaces() {
        Parameter parameter = new Parameter("   field          number   ");

        assertThat(parameter.getName(), equalTo("field"));
        assertThat(parameter.getType(), equalTo("number"));
        assertThat(parameter.isOut(), equalTo(false));
    }
}
