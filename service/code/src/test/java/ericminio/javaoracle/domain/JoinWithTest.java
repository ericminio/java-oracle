package ericminio.javaoracle.domain;

import org.hamcrest.MatcherAssert;
import org.junit.Test;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.equalTo;

public class JoinWithTest {

    @Test
    public void worksAsExpected() {
        MatcherAssert.assertThat(new JoinWith(" ").please(Arrays.asList("hello", "world")), equalTo("hello world"));
    }

    @Test
    public void trimFirst() {
        MatcherAssert.assertThat(new JoinWith(" ").please(Arrays.asList("hello\n ", "world")), equalTo("hello world"));
    }
}
