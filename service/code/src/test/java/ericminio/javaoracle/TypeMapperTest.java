package ericminio.javaoracle;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class TypeMapperTest {

    @Test
    public void knowsAboutInteger() {
        assertThat(new TypeMapper().typeOf("INTEGER"), equalTo("Types.INTEGER"));
        assertThat(new TypeMapper().getterOf("INTEGER"), equalTo("getInt"));
        assertThat(new TypeMapper().setterOf("INTEGER"), equalTo("setInt"));
        assertThat(new TypeMapper().javaTypeOf("INTEGER"), equalTo("int"));
    }

    @Test
    public void knowsAboutString() {
        assertThat(new TypeMapper().typeOf("varchar2"), equalTo("Types.VARCHAR"));
        assertThat(new TypeMapper().getterOf("varchar2"), equalTo("getString"));
        assertThat(new TypeMapper().setterOf("varchar2"), equalTo("setString"));
        assertThat(new TypeMapper().javaTypeOf("varchar2"), equalTo("String"));
    }
}
