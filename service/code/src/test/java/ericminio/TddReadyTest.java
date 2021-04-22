package ericminio;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class TddReadyTest {

    @Test
    public void assertionsAreAvailable() {
        assertThat(1+1, equalTo(2));
    }
}