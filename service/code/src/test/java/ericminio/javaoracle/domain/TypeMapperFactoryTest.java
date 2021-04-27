package ericminio.javaoracle.domain;

import ericminio.javaoracle.domain.TypeMapperFactory;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class TypeMapperFactoryTest {

    @Test
    public void knowsAboutInteger() {
        assertThat(new TypeMapperFactory().of("INTEGER").sqlType(), equalTo("Types.INTEGER"));
        assertThat(new TypeMapperFactory().of("INTEGER").getter(), equalTo("getInt"));
        assertThat(new TypeMapperFactory().of("INTEGER").setter(), equalTo("setInt"));
        assertThat(new TypeMapperFactory().of("INTEGER").javaType(), equalTo("int"));
    }

    @Test
    public void knowsAboutString() {
        assertThat(new TypeMapperFactory().of("VARCHAR2").sqlType(), equalTo("Types.VARCHAR"));
        assertThat(new TypeMapperFactory().of("VARCHAR2").getter(), equalTo("getString"));
        assertThat(new TypeMapperFactory().of("VARCHAR2").setter(), equalTo("setString"));
        assertThat(new TypeMapperFactory().of("VARCHAR2").javaType(), equalTo("String"));
    }
}
