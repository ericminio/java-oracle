package ericminio.javaoracle.domain;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class BuildMethodParameterListTest {

    private BuildMethodParameterList buildMethodParameterList;

    @Before
    public void sut() {
        List<List<String>> typeSpecifications = Arrays.asList(
                Arrays.asList("create type any_type as object(value number)")
        );
        TypeMapperFactory typeMapperFactory = new TypeMapperFactory(typeSpecifications);
        buildMethodParameterList = new BuildMethodParameterList(typeMapperFactory);
    }

    @Test
    public void noParameterResultInEmptyList() {
        Parameters parameters = new Parameters();
        assertThat(buildMethodParameterList.please(parameters), equalTo(""));
    }

    @Test
    public void listOfOneParameter() {
        Parameters parameters = new Parameters();
        parameters.add("param number");
        assertThat(buildMethodParameterList.please(parameters), equalTo("BigDecimal param"));
    }

    @Test
    public void listOfTwoParameters() {
        Parameters parameters = new Parameters();
        parameters.add("field1 number");
        parameters.add("field2 varchar2");
        assertThat(buildMethodParameterList.please(parameters), equalTo("BigDecimal field1, String field2"));
    }

    @Test
    public void resistsVarcharSize() {
        Parameters parameters = new Parameters();
        parameters.add("field1 number");
        parameters.add("field2 varchar2(5)");
        assertThat(buildMethodParameterList.please(parameters), equalTo("BigDecimal field1, String field2"));
    }

    @Test
    public void camelCase() {
        Parameters parameters = new Parameters();
        parameters.add("any_field any_type");
        assertThat(buildMethodParameterList.please(parameters), equalTo("AnyType anyField"));
    }
}
