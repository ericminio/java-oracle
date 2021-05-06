package ericminio.javaoracle.domain;

import org.junit.Test;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class RemoveCommentsTest {

    @Test
    public void worksForOneLine() {
        String actual = new JoinWith(" ").please(new RemoveComments().please(Arrays.asList("hello--ignore", "world")));
        assertThat(actual, equalTo("hello world"));
    }
}
