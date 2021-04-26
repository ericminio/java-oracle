package ericminio.javaoracle;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class TypeMapperTest {

    @Test
    public void knowsAboutInteger() {
        assertThat(new TypeMapper().typeOf("INTEGER"), equalTo("Types.INTEGER"));
        assertThat(new TypeMapper().accessorOf("INTEGER"), equalTo("getInt"));
    }
}
