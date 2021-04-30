package ericminio.javaoracle.domain;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class TypeMapperFactoryTest {

    @Test
    public void knowsAboutNumber() {
        assertThat(new TypeMapperFactory().of("number").functionParameterSettingStatement(), equalTo("statement.setBigDecimal(index, field);"));
        assertThat(new TypeMapperFactory().of("number").javaType(), equalTo("BigDecimal"));
        assertThat(new TypeMapperFactory().of("number").sqlInputRead(), equalTo("stream.readBigDecimal()"));
        assertThat(new TypeMapperFactory().of("number").sqlOutputWrite(), equalTo("stream.writeBigDecimal(this.getField());"));
        assertThat(new TypeMapperFactory().of("number").methodReturnStatement(), equalTo("return (BigDecimal) resultSet.getObject(1);"));
    }

    @Test
    public void knowsAboutString() {
        assertThat(new TypeMapperFactory().of("VARCHAR2").functionParameterSettingStatement(), equalTo("statement.setString(index, field);"));
        assertThat(new TypeMapperFactory().of("VARCHAR2").javaType(), equalTo("String"));
        assertThat(new TypeMapperFactory().of("VARCHAR2").sqlInputRead(), equalTo("stream.readString()"));
        assertThat(new TypeMapperFactory().of("VARCHAR2").sqlOutputWrite(), equalTo("stream.writeString(this.getField());"));
        assertThat(new TypeMapperFactory().of("VARCHAR2").methodReturnStatement(), equalTo("return (String) resultSet.getObject(1);"));
    }

    @Test
    public void knowsAboutDate() {
        assertThat(new TypeMapperFactory().of("DATE").functionParameterSettingStatement(), equalTo("statement.setTimestamp(index, new java.sql.Timestamp(field.getTime()));"));
        assertThat(new TypeMapperFactory().of("DATE").javaType(), equalTo("Date"));
        assertThat(new TypeMapperFactory().of("DATE").sqlInputRead(), equalTo("new Date(stream.readTimestamp().getTime())"));
        assertThat(new TypeMapperFactory().of("DATE").sqlOutputWrite(), equalTo("stream.writeTimestamp(new java.sql.Timestamp(this.getField().getTime()));"));
        assertThat(new TypeMapperFactory().of("DATE").methodReturnStatement(), equalTo("return new Date( ((java.sql.Timestamp) resultSet.getObject(1)).getTime() );"));
    }

    @Test
    public void knowsAboutCustomType() {
        assertThat(new TypeMapperFactory().of("any_type").functionParameterSettingStatement(), equalTo("statement.setObject(index, field);"));
        assertThat(new TypeMapperFactory().of("any_type").javaType(), equalTo("AnyType"));
        assertThat(new TypeMapperFactory().of("any_type").sqlInputRead(), equalTo("stream.readObject()"));
        assertThat(new TypeMapperFactory().of("any_type").sqlOutputWrite(), equalTo("stream.writeObject(this.getField());"));
        assertThat(new TypeMapperFactory().of("any_type").methodReturnStatement(), equalTo("return (AnyType) resultSet.getObject(1);"));
    }
}
