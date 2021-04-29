package ericminio.javaoracle.domain;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class BuildEqualsReturnValueTest {

    @Test
    public void one() {
        Parameters parameters = new Parameters();
        parameters.add("any_field any_type");
        assertThat(new BuildEqualsReturnValue().please(parameters), equalTo("" +
                "                this.getAnyField().equals(other.getAnyField())"
        ));
    }

    @Test
    public void two() {
        Parameters parameters = new Parameters();
        parameters.add("any_field any_type");
        parameters.add("another_field any_type");
        assertThat(new BuildEqualsReturnValue().please(parameters), equalTo("" +
                "                this.getAnyField().equals(other.getAnyField())\n" +
                "                && this.getAnotherField().equals(other.getAnotherField())"
        ));
    }
}
