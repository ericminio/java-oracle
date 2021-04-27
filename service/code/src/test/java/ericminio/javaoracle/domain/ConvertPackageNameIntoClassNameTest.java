package ericminio.javaoracle.domain;

import ericminio.javaoracle.domain.ConvertPackageNameIntoClassName;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class ConvertPackageNameIntoClassNameTest {

    @Test
    public void firstLetterIsUpperCased() {
        assertThat(new ConvertPackageNameIntoClassName().please("hello"), equalTo("Hello"));
    }

    @Test
    public void resistsUpperCase() {
        assertThat(new ConvertPackageNameIntoClassName().please("HELLO"), equalTo("Hello"));
    }

    @Test
    public void snakeCaseIntoPascalCase() {
        assertThat(new ConvertPackageNameIntoClassName().please("hello_world"), equalTo("HelloWorld"));
    }

}