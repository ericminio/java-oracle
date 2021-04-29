package ericminio.javaoracle.domain;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class TypeMapperFactoryTest {

    @Test
    public void knowsAboutInteger() {
        assertThat(new TypeMapperFactory().of("INTEGER").resultSetGetter(), equalTo("getInt"));
        assertThat(new TypeMapperFactory().of("INTEGER").sqlStatementSetter(), equalTo("setInt"));
        assertThat(new TypeMapperFactory().of("INTEGER").javaType(), equalTo("Integer"));
        assertThat(new TypeMapperFactory().of("INTEGER").sqlInputRead(), equalTo("readInt"));
        assertThat(new TypeMapperFactory().of("INTEGER").sqlOutputWrite(), equalTo("writeInt"));
    }

    @Test
    public void knowsAboutString() {
        assertThat(new TypeMapperFactory().of("VARCHAR2").resultSetGetter(), equalTo("getString"));
        assertThat(new TypeMapperFactory().of("VARCHAR2").sqlStatementSetter(), equalTo("setString"));
        assertThat(new TypeMapperFactory().of("VARCHAR2").javaType(), equalTo("String"));
        assertThat(new TypeMapperFactory().of("VARCHAR2").sqlInputRead(), equalTo("readString"));
        assertThat(new TypeMapperFactory().of("VARCHAR2").sqlOutputWrite(), equalTo("writeString"));
    }

    @Test
    public void knowsAboutCustomType() {
        assertThat(new TypeMapperFactory().of("any_type").resultSetGetter(), equalTo("getObject"));
        assertThat(new TypeMapperFactory().of("any_type").sqlStatementSetter(), equalTo("setObject"));
        assertThat(new TypeMapperFactory().of("any_type").javaType(), equalTo("AnyType"));
        assertThat(new TypeMapperFactory().of("any_type").sqlInputRead(), equalTo("readObject"));
        assertThat(new TypeMapperFactory().of("any_type").sqlOutputWrite(), equalTo("writeObject"));
    }
}
