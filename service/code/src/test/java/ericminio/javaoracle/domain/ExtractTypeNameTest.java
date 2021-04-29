package ericminio.javaoracle.domain;

import org.junit.Test;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class ExtractTypeNameTest {

    @Test
    public void fromCreateStatement() {
        assertThat(new ExtractTypeName().please(Arrays.asList(
                "type beautiful_type as object\n",
                "(\n",
                "   value number\n",
                ")"
        )), equalTo("beautiful_type"));
    }
}