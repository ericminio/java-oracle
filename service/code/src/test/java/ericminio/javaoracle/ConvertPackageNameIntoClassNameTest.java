package ericminio.javaoracle;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class ConvertPackageNameIntoClassNameTest {

    @Test
    public void firstLetterIsUpperCased() {
        assertThat(new ConvertPackageNameIntoClassName().please("hello"), equalTo("Hello"));
    }

    @Test
    public void resistsUpperCase() {
        assertThat(new ConvertPackageNameIntoClassName().please("HELLO"), equalTo("Hello"));
    }
}