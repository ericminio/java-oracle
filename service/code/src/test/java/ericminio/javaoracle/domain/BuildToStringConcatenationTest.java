package ericminio.javaoracle.domain;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class BuildToStringConcatenationTest {

    private BuildToStringConcatenation buildToStringConcatenation;

    @Before
    public void sut() {
        buildToStringConcatenation = new BuildToStringConcatenation(new TypeMapperFactory());
    }

    @Test
    public void one() {
        Parameters parameters = new Parameters();
        parameters.add("any_field any_type");
        assertThat(buildToStringConcatenation.please(parameters), equalTo("" +
                "                + \" anyField=\" + (this.getAnyField() == null ? \"null\" : this.getAnyField().toString())"
        ));
    }

    @Test
    public void two() {
        Parameters parameters = new Parameters();
        parameters.add("any_field any_type");
        parameters.add("another_field any_type");
        assertThat(buildToStringConcatenation.please(parameters), equalTo("" +
                "                + \" anyField=\" + (this.getAnyField() == null ? \"null\" : this.getAnyField().toString())\n" +
                "                + \", anotherField=\" + (this.getAnotherField() == null ? \"null\" : this.getAnotherField().toString())"
        ));
    }
}
