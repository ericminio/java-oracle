package ericminio.javaoracle.domain;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import ericminio.javaoracle.domain.ConvertFunctionNameIntoMethodName;
import org.junit.Test;

public class ConvertFunctionNameIntoMethodNameTest {

    @Test
    public void camelCase() {
        assertThat(new ConvertFunctionNameIntoMethodName().please("hello_world"),
            equalTo("helloWorld"));
    }
}