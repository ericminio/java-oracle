package ericminio.javaoracle.domain;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class BuildEqualsReturnValueTest {

    BuildEqualsReturnValue buildEqualsReturnValue;

    @Before
    public void sut() {
        buildEqualsReturnValue = new BuildEqualsReturnValue(new TypeMapperFactory());
    }

    @Test
    public void one() {
        Parameters parameters = new Parameters();
        parameters.add("any_field any_type");
        assertThat(buildEqualsReturnValue.please(parameters), equalTo("" +
                "                (this.getAnyField() == null ? other.getAnyField() == null : this.getAnyField().equals(other.getAnyField()))"
        ));
    }

    @Test
    public void two() {
        Parameters parameters = new Parameters();
        parameters.add("any_field any_type");
        parameters.add("another_field any_type");
        assertThat(buildEqualsReturnValue.please(parameters), equalTo("" +
                "                (this.getAnyField() == null ? other.getAnyField() == null : this.getAnyField().equals(other.getAnyField()))\n" +
                "                && (this.getAnotherField() == null ? other.getAnotherField() == null : this.getAnotherField().equals(other.getAnotherField()))"
        ));
    }
}
