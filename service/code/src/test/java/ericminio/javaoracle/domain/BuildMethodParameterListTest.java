package ericminio.javaoracle.domain;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class BuildMethodParameterListTest {

    @Test
    public void noParameterResultInEmptyList() {
        Parameters parameters = new Parameters();
        assertThat(new BuildMethodParameterList().please(parameters), equalTo(""));
    }

    @Test
    public void listOfOneParameter() {
        Parameters parameters = new Parameters();
        parameters.add("param number");
        assertThat(new BuildMethodParameterList().please(parameters), equalTo("BigDecimal param"));
    }

    @Test
    public void listOfTwoParameters() {
        Parameters parameters = new Parameters();
        parameters.add("field1 number");
        parameters.add("field2 varchar2");
        assertThat(new BuildMethodParameterList().please(parameters), equalTo("BigDecimal field1, String field2"));
    }

    @Test
    public void resistsVarcharSize() {
        Parameters parameters = new Parameters();
        parameters.add("field1 number");
        parameters.add("field2 varchar2(5)");
        assertThat(new BuildMethodParameterList().please(parameters), equalTo("BigDecimal field1, String field2"));
    }

    @Test
    public void camelCase() {
        Parameters parameters = new Parameters();
        parameters.add("any_field any_type");
        assertThat(new BuildMethodParameterList().please(parameters), equalTo("AnyType anyField"));
    }
}
