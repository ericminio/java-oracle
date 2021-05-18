package ericminio.javaoracle.domain;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;

public class GenerateArrayTypeCodeFromVarrayTest {

    private String code;

    @Before
    public void generateCode() throws IOException {
        List<String> typeSpecification = Arrays.asList("type array_of_any_type as varray(15) of random_type;");
        GenerateArrayTypeCode generateArrayTypeCode = new GenerateArrayTypeCode();
        TypeMapperFactory typeMapperFactory = new TypeMapperFactory(Arrays.asList(Arrays.asList("create type random_type as object(value number)")));
        code = generateArrayTypeCode.please(typeSpecification, typeMapperFactory);
    }

    @Test
    public void className() {
        assertThat(code, containsString("public class ArrayOfAnyType {"));
    }

    @Test
    public void fieldDeclaration() {
        assertThat(code, containsString("private RandomType[] array"));
    }

    @Test
    public void getArray() {
        assertThat(code, containsString("public RandomType[] getArray() {"));
    }

    @Test
    public void setArray() {
        assertThat(code, containsString("public void setArray(RandomType[] array) {"));
    }

    @Test
    public void getElement() {
        assertThat(code, containsString("public RandomType getElement(long index) {"));
    }

    @Test
    public void equalsContent() {
        assertThat(code, containsString("if (! (o instanceof ArrayOfAnyType)) {"));
        assertThat(code, containsString("ArrayOfAnyType other = (ArrayOfAnyType) o;"));
    }

    @Test
    public void staticBuilder() {
        assertThat(code, containsString("public static ArrayOfAnyType with(Object[] incoming) {"));
        assertThat(code, containsString("RandomType[] array = new RandomType[incoming.length];"));
        assertThat(code, containsString("ArrayOfAnyType arrayType = new ArrayOfAnyType();"));
    }
}
