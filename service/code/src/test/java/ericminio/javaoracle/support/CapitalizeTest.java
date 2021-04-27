package ericminio.javaoracle.support;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class CapitalizeTest {

    @Test
    public void modifiesFirstLetter() {
        assertThat(new Capitalize().please("hello"),
                equalTo("Hello"));
    }

    @Test
    public void keepsUpperCaseAsItIs() {
        assertThat(new Capitalize().please("HELLO"),
                equalTo("HELLO"));
    }

    @Test
    public void helpstoConvertFromCamelCaseToPascalCase() {
        assertThat(new Capitalize().please("helloWorld"),
                equalTo("HelloWorld"));
    }
}