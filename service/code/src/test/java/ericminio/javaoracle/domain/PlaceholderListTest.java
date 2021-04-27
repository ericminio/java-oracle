package ericminio.javaoracle.domain;

import ericminio.javaoracle.domain.PlaceholderList;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class PlaceholderListTest {

    @Test
    public void emptyStringWhenEmpty() {
        assertThat(new PlaceholderList().please(0), equalTo(""));
    }

    @Test
    public void one() {
        assertThat(new PlaceholderList().please(1), equalTo("?"));
    }

    @Test
    public void two() {
        assertThat(new PlaceholderList().please(2), equalTo("?, ?"));
    }
}
