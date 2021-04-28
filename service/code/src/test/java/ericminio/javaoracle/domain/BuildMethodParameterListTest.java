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
        parameters.add("param integer");
        assertThat(new BuildMethodParameterList().please(parameters), equalTo("Integer param"));
    }

    @Test
    public void listOfTwoParameters() {
        Parameters parameters = new Parameters();
        parameters.add("field1 integer");
        parameters.add("field2 varchar2");
        assertThat(new BuildMethodParameterList().please(parameters), equalTo("Integer field1, String field2"));
    }

    @Test
    public void resistsVarcharSize() {
        Parameters parameters = new Parameters();
        parameters.add("field1 integer");
        parameters.add("field2 varchar2(5)");
        assertThat(new BuildMethodParameterList().please(parameters), equalTo("Integer field1, String field2"));
    }
}
