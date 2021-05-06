package ericminio.javaoracle.domain;

import org.junit.Test;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

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

    @Test
    public void resistsLowerCase() {
        assertThat(new ExtractPackageName().please(Arrays.asList(
                "package beautiful\n",
                "AS\n",
                "END beautiful;"
        )), equalTo("beautiful"));
    }

    @Test
    public void resistsNameContainingPackage() {
        assertThat(new ExtractPackageName().please(Arrays.asList(
                "package any_package\n",
                "AS\n",
                "END any_package;"
        )), equalTo("any_package"));
    }

    @Test
    public void resistsAsOnCreationLine() {
        assertThat(new ExtractPackageName().please(Arrays.asList(
                "package any_package AS\n",
                "END any_package;"
        )), equalTo("any_package"));
    }

    @Test
    public void resistsIsOnCreationLine() {
        assertThat(new ExtractPackageName().please(Arrays.asList(
                "package any_package IS\n",
                "END any_package;"
        )), equalTo("any_package"));
    }

    @Test
    public void resistsCreateStatement() {
        assertThat(new ExtractPackageName().please(Arrays.asList(
                "create package any_package IS\n",
                "END any_package;"
        )), equalTo("any_package"));
    }

    @Test
    public void resistscreateOrReplaceStatement() {
        assertThat(new ExtractPackageName().please(Arrays.asList(
                "create or replace package any_package IS\n",
                "END any_package;"
        )), equalTo("any_package"));
    }

    @Test
    public void resistsPackageNameOnNewLine() {
        assertThat(new ExtractPackageName().please(Arrays.asList(
                "create or replace package\n",
                "any_package IS\n",
                "END any_package;"
        )), equalTo("any_package"));
    }

    @Test
    public void resistsComments() {
        assertThat(new ExtractPackageName().please(Arrays.asList(
                "create or replace package\n -- ignore this",
                "any_package-- ignore that\n",
                "IS END any_package;"
        )), equalTo("any_package"));
    }
}