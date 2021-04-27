package ericminio.javaoracle.support;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class PascalCaseTest {

    @Test
    public void snakeCaseIntoPascalCase() {
        assertThat(new PascalCase().please("hello_world"),
                equalTo("HelloWorld"));
    }

    @Test
    public void singleWorld() {
        assertThat(new PascalCase().please("hello"),
                equalTo("Hello"));
    }

    @Test
    public void capital() {
        assertThat(new PascalCase().please("HELLO"),
                equalTo("Hello"));
    }

    @Test
    public void upperCase() {
        assertThat(new PascalCase().please("Hello"),
                equalTo("Hello"));
    }

}