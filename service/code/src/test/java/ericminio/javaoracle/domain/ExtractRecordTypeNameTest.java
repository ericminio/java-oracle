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
    public void supportsIsVarrayOfCustomType() {
        assertThat(new ExtractRecordTypeName().please(Arrays.asList("create or replace type beautiful_type is varray(30) of custom_type")),
                equalTo("custom_type"));
    }

    @Test
    public void supportsIsVarrayOfCustomTypeWithTrailingSemicolon() {
        assertThat(new ExtractRecordTypeName().please(Arrays.asList("create or replace type beautiful_type is varray(30) of custom_type;")),
                equalTo("custom_type"));
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

    @Test
    public void resistsTrailingSemicolon() {
        assertThat(new ExtractRecordTypeName().please(Arrays.asList("create or replace type beautiful_type is table of number;")),
                equalTo("number"));
    }

    @Test
    public void resistsExtraSpaceAfterTrailingSemicolon() {
        assertThat(new ExtractRecordTypeName().please(Arrays.asList("create or replace type beautiful_type is table of number;  ")),
                equalTo("number"));
    }

    @Test
    public void ignoresComment() {
        assertThat(new ExtractRecordTypeName().please(Arrays.asList("create or replace type beautiful_type is table of number;-- ignore me")),
                equalTo("number"));
    }

    @Test
    public void ignoresCommentAfterSpace() {
        assertThat(new ExtractRecordTypeName().please(Arrays.asList("create or replace type beautiful_type is table of number; -- ignore me")),
                equalTo("number"));
    }

    @Test
    public void supportsUpperCaseKeywords() {
        assertThat(new ExtractRecordTypeName().please(Arrays.asList("TYPE array_type IS varray(20) of custom_type;")),
                equalTo("custom_type"));
    }

    @Test
    public void supportsMissingSpaceBeforeOf() {
        assertThat(new ExtractRecordTypeName().please(Arrays.asList("TYPE array_type IS varray(20)of custom_type;")), equalTo("custom_type"));
        assertThat(new ExtractRecordTypeName().please(Arrays.asList("TYPE array_type AS varray(20)of custom_type;")), equalTo("custom_type"));
    }

    @Test
    public void resistsSemicolonOnNewLine() {
        assertThat(new ExtractRecordTypeName().please(Arrays.asList(
                "create or replace type beautiful_type is table of number",
                ";"
                )),
                equalTo("number"));
    }

    @Test
    public void ignoresIndexBy() {
        assertThat(new ExtractRecordTypeName().please(Arrays.asList(
                "type beautiful_type is table of number index by binary_integer;"
                )),
                equalTo("number"));
    }
}