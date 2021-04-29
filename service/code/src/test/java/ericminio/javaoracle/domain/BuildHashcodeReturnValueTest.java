package ericminio.javaoracle.domain;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class BuildHashcodeReturnValueTest {

    @Test
    public void one() {
        Parameters parameters = new Parameters();
        parameters.add("any_field integer");
        assertThat(new BuildHashcodeReturnValue().please(parameters), equalTo("" +
                "                this.getAnyField().hashCode()"
        ));
    }

    @Test
    public void two() {
        Parameters parameters = new Parameters();
        parameters.add("any_field integer");
        parameters.add("another_field integer");
        assertThat(new BuildHashcodeReturnValue().please(parameters), equalTo("" +
                "                this.getAnyField().hashCode()\n" +
                "                + this.getAnotherField().hashCode()"
        ));
    }
}
