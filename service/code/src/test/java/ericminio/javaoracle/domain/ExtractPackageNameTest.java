package ericminio.javaoracle.domain;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.util.Arrays;

import ericminio.javaoracle.domain.ExtractPackageName;
import org.junit.Test;

public class ExtractPackageNameTest {

    @Test
    public void works() {
        assertThat(new ExtractPackageName().please(Arrays.asList(
            "PACKAGE beautiful\n",
            "AS\n",
            "END beautiful;"
        )), equalTo("beautiful"));
    }

    @Test
    public void returnsLowerCase() {
        assertThat(new ExtractPackageName().please(Arrays.asList(
            "PACKAGE EXPLORATION\n",
            "AS\n",
            "END EXPLORATION;"
        )), equalTo("exploration"));
    }
}