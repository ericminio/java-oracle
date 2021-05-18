package ericminio.javaoracle.domain;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;

public class GenerateArrayTypeOfVarcharTest {

    private String code;

    @Before
    public void generateCode() throws IOException {
        List<String> typeSpecification = Arrays.asList("type array_of_any_type as table of varchar(100);");
        GenerateArrayTypeCode generateArrayTypeCode = new GenerateArrayTypeCode();
        code = generateArrayTypeCode.please(typeSpecification, new TypeMapperFactory());
    }

    @Test
    public void className() {
        assertThat(code, containsString("public class ArrayOfAnyType {"));
    }

    @Test
    public void fieldDeclaration() {
        assertThat(code, containsString("private String[] array"));
    }

    @Test
    public void getArray() {
        assertThat(code, containsString("public String[] getArray() {"));
    }

    @Test
    public void setArray() {
        assertThat(code, containsString("public void setArray(String[] array) {"));
    }

    @Test
    public void getElement() {
        assertThat(code, containsString("public String getElement(long index) {"));
    }

    @Test
    public void equalsContent() {
        assertThat(code, containsString("if (! (o instanceof ArrayOfAnyType)) {"));
        assertThat(code, containsString("ArrayOfAnyType other = (ArrayOfAnyType) o;"));
    }

    @Test
    public void staticBuilder() {
        assertThat(code, containsString("public static ArrayOfAnyType with(Object[] incoming) {"));
        assertThat(code, containsString("String[] array = new String[incoming.length];"));
        assertThat(code, containsString("ArrayOfAnyType arrayType = new ArrayOfAnyType();"));
    }
}
