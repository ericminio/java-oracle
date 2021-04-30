package ericminio.javaoracle.support;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class CamelCaseTest {

    @Test
    public void snakeCaseIntoCamelCase() {
        assertThat(new CamelCase().please("hello_world"),
            equalTo("helloWorld"));
    }

    @Test
    public void upperCaseSnakeCaseIntoCamelCase() {
        assertThat(new CamelCase().please("HELLO_WORLD"),
            equalTo("helloWorld"));
    }

    @Test
    public void singleWord() {
        assertThat(new CamelCase().please("hello"),
                equalTo("hello"));
    }

    @Test
    public void capital() {
        assertThat(new CamelCase().please("Hello"),
                equalTo("hello"));
    }

    @Test
    public void upperCase() {
        assertThat(new CamelCase().please("HELLO"),
                equalTo("hello"));
    }
}