package ericminio.javaoracle;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.util.Arrays;

import org.junit.Test;

public class CapitalizeTest {

    @Test
    public void works() {
        assertThat(new Capitalize().please("hello"), equalTo("Hello"));
    }

    @Test
    public void resistsUpperCase() {
        assertThat(new Capitalize().please("HELLO"), equalTo("Hello"));
    }
}