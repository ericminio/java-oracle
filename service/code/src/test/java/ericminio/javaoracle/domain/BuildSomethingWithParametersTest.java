package ericminio.javaoracle.domain;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class BuildSomethingWithParametersTest {

    private class Dummy extends BuildSomethingWithParameters {

        public Dummy(TypeMapperFactory typeMapperFactory) {
            super(typeMapperFactory);
        }

        @Override
        protected String modify(String output, int index, Parameter parameter, boolean isLast) {
            String name = parameter.getName();
            String type = parameter.getType();
            return output
                + (index != 0 ? ", " : "")
                + name + " " + type
                + (isLast ? " :)": "")
                    ;
        }
    }

    private Dummy dummy;

    @Before
    public void newSpy() {
        dummy = new Dummy(new TypeMapperFactory());
    }

    @Test
    public void worksAsExpected() {
        Parameters parameters = new Parameters();
        parameters.add("any_field any_type");
        parameters.add("another_field another_type");
        assertThat(dummy.please(parameters), equalTo("any_field any_type, another_field another_type :)"));
    }

    @Test
    public void ignoresOptionalInKeyword() {
        Parameters parameters = new Parameters();
        parameters.add("any_field in any_type");
        assertThat(dummy.please(parameters), equalTo("any_field any_type :)"));
    }

    @Test
    public void lowerCases() {
        Parameters parameters = new Parameters();
        parameters.add("ANY_FIELD IN ANY_TYPE");
        assertThat(dummy.please(parameters), equalTo("any_field any_type :)"));
    }

    @Test
    public void ignoresExtraSpaces() {
        Parameters parameters = new Parameters();
        parameters.add("         any_field                 in   any_type         ");
        assertThat(dummy.please(parameters), equalTo("any_field any_type :)"));
    }
}
