package ericminio.javaoracle;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class ConvertFunctionNameIntoMethodNameTest {

    @Test
    public void camelCase() {
        assertThat(new ConvertFunctionNameIntoMethodName().please("hello_world"), 
            equalTo("helloWorld"));
    }
}