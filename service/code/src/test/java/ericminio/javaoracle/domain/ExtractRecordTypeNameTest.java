package ericminio.javaoracle.domain;

import org.junit.Test;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class ExtractRecordTypeNameTest {

    @Test
    public void supportsAsVarray() {
        assertThat(new ExtractRecordTypeName().please(Arrays.asList("create or replace type beautiful_type as varray(3) of number")),
                equalTo("number"));
    }

    @Test
    public void supportsAsVarrayWithoutSize() {
        assertThat(new ExtractRecordTypeName().please(Arrays.asList("create or replace type beautiful_type as varray of number")),
                equalTo("number"));
    }

    @Test
    public void supportsIsVarray() {
        assertThat(new ExtractRecordTypeName().please(Arrays.asList("create or replace type beautiful_type is varray(30) of number")),
                equalTo("number"));
    }

    @Test
    public void supportsIsVarrayWithoutSize() {
        assertThat(new ExtractRecordTypeName().please(Arrays.asList("create or replace type beautiful_type is varray of number")),
                equalTo("number"));
    }

    @Test
    public void supportsAsTable() {
        assertThat(new ExtractRecordTypeName().please(Arrays.asList("create or replace type beautiful_type as table of number")),
                equalTo("number"));
    }

    @Test
    public void supportsIsTable() {
        assertThat(new ExtractRecordTypeName().please(Arrays.asList("create or replace type beautiful_type is table of number")),
                equalTo("number"));
    }
}