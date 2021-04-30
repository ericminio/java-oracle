package ericminio.javaoracle.domain;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class BuildHashcodeReturnValueTest {

    @Test
    public void one() {
        Parameters parameters = new Parameters();
        parameters.add("any_field any_type");
        assertThat(new BuildHashcodeReturnValue().please(parameters), equalTo("" +
                "                (this.getAnyField() == null ? 0 : this.getAnyField().hashCode())"
        ));
    }

    @Test
    public void two() {
        Parameters parameters = new Parameters();
        parameters.add("any_field any_type");
        parameters.add("another_field any_type");
        assertThat(new BuildHashcodeReturnValue().please(parameters), equalTo("" +
                "                (this.getAnyField() == null ? 0 : this.getAnyField().hashCode())\n" +
                "                + (this.getAnotherField() == null ? 0 : this.getAnotherField().hashCode())"
        ));
    }
}
