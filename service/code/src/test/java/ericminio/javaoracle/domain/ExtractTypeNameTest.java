package ericminio.javaoracle.domain;

import org.junit.Test;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class ExtractTypeNameTest {

    @Test
    public void fromTypeStatement() {
        assertThat(new ExtractTypeName().please(Arrays.asList(
                "type beautiful_type as object\n",
                "(\n",
                "   value number\n",
                ")"
        )), equalTo("beautiful_type"));
    }

    @Test
    public void fromCreateTypeStatement() {
        assertThat(new ExtractTypeName().please(Arrays.asList(
                "create or replace type beautiful_type as object\n",
                "(\n",
                "   value number\n",
                ")"
        )), equalTo("beautiful_type"));
    }

    @Test
    public void resistsNameOnNewLine() {
        assertThat(new ExtractTypeName().please(Arrays.asList(
                "create or replace type\n",
                "beautiful_type as object\n",
                "(\n",
                "   value number\n",
                ")"
        )), equalTo("beautiful_type"));
    }

    @Test
    public void supportsAsVarray() {
        assertThat(new ExtractTypeName().please(Arrays.asList("create or replace type beautiful_type as varray(3) of number")),
                equalTo("beautiful_type"));
    }

    @Test
    public void supportsIsVarray() {
        assertThat(new ExtractTypeName().please(Arrays.asList("create or replace type beautiful_type is varray(3) of number")),
                equalTo("beautiful_type"));
    }

    @Test
    public void supportsAsTable() {
        assertThat(new ExtractTypeName().please(Arrays.asList("create or replace type beautiful_type as table of number")),
                equalTo("beautiful_type"));
    }

    @Test
    public void supportsIsTable() {
        assertThat(new ExtractTypeName().please(Arrays.asList("create or replace type beautiful_type is table of number")),
                equalTo("beautiful_type"));
    }

    @Test
    public void resistsDoubleQuotes() {
        assertThat(new ExtractTypeName().please(Arrays.asList(
                "create type \"beautiful_type\" as object(value number)"
        )), equalTo("beautiful_type"));
    }

    @Test
    public void supportsIsRefCursor() {
        assertThat(new ExtractTypeName().please(Arrays.asList(
                "type beautiful_type is ref cursor"
        )), equalTo("beautiful_type"));
    }

    @Test
    public void supportsAsRefCursor() {
        assertThat(new ExtractTypeName().please(Arrays.asList(
                "type beautiful_type as ref cursor"
        )), equalTo("beautiful_type"));
    }
}