package ericminio.javaoracle.domain;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class TypeMapperFactoryTest {

    private TypeMapperFactory typeMapperFactory;

    @Before
    public void initTypes() {
        List<List<String>> typeSpecifications = Arrays.asList(
                Arrays.asList("create or replace type any_type as object (value number);"),
                Arrays.asList("create or replace type any_table_type as table of any_type;"),
                Arrays.asList("create or replace type any_varray_type as varray(15) of any_type;"),
                Arrays.asList("create or replace type missing_semicolon as table of any_type"),
                Arrays.asList("create or replace type extra_spaces as table of any_type    ")
                );
        typeMapperFactory = new TypeMapperFactory(typeSpecifications);
    }

    @Test
    public void knowsWhenTypeIsNotArrayType() {
        assertThat(typeMapperFactory.isArrayType("any_type"), equalTo(false));
    }

    @Test
    public void knowsTheTableTypeIsArrayType() {
        assertThat(typeMapperFactory.isArrayType("any_table_type"), equalTo(true));
    }

    @Test
    public void knowsTheVarrayTypeIsArrayType() {
        assertThat(typeMapperFactory.isArrayType("any_varray_type"), equalTo(true));
    }

    @Test
    public void knowsTheRecordTypeOfGivenTableType() {
        assertThat(typeMapperFactory.recordTypeOfArrayType("any_table_type"), equalTo("any_type"));
    }

    @Test
    public void knowsTheRecordTypeOfGivenVarrayType() {
        assertThat(typeMapperFactory.recordTypeOfArrayType("any_varray_type"), equalTo("any_type"));
    }

    @Test(expected = RuntimeException.class)
    public void recordTypeGetterResistsNonArrayType() {
        typeMapperFactory.recordTypeOfArrayType("any_type");
    }

    @Test(expected = RuntimeException.class)
    public void recordTypeGetterResistsUnknownType() {
        typeMapperFactory.recordTypeOfArrayType("unknown");
    }

    @Test(expected = RuntimeException.class)
    public void recordTypeGetterResistsMissingSemicolon() {
        assertThat(typeMapperFactory.recordTypeOfArrayType("missing_semicolon"), equalTo("any_type"));
    }

    @Test(expected = RuntimeException.class)
    public void recordTypeGetterResistsExtraSpaces() {
        assertThat(typeMapperFactory.recordTypeOfArrayType("extra_spaces"), equalTo("any_type"));
    }

    @Test
    public void knowsAboutNumber() {
        assertThat(typeMapperFactory.of("number").functionParameterSettingStatement(), equalTo("statement.setBigDecimal(index, field);"));
        assertThat(typeMapperFactory.of("number").javaType(), equalTo("BigDecimal"));
        assertThat(typeMapperFactory.of("number").sqlInputRead(), equalTo("stream.readBigDecimal()"));
        assertThat(typeMapperFactory.of("number").sqlOutputWrite(), equalTo("stream.writeBigDecimal(this.getField());"));
        assertThat(typeMapperFactory.of("number").methodReturnStatement(), equalTo("return (BigDecimal) resultSet.getObject(1);"));
    }

    @Test
    public void knowsAboutString() {
        assertThat(typeMapperFactory.of("VARCHAR2").functionParameterSettingStatement(), equalTo("statement.setString(index, field);"));
        assertThat(typeMapperFactory.of("VARCHAR2").javaType(), equalTo("String"));
        assertThat(typeMapperFactory.of("VARCHAR2").sqlInputRead(), equalTo("stream.readString()"));
        assertThat(typeMapperFactory.of("VARCHAR2").sqlOutputWrite(), equalTo("stream.writeString(this.getField());"));
        assertThat(typeMapperFactory.of("VARCHAR2").methodReturnStatement(), equalTo("return (String) resultSet.getObject(1);"));
    }

    @Test
    public void knowsAboutDate() {
        assertThat(typeMapperFactory.of("DATE").functionParameterSettingStatement(), equalTo("statement.setTimestamp(index, new java.sql.Timestamp(field.getTime()));"));
        assertThat(typeMapperFactory.of("DATE").javaType(), equalTo("Date"));
        assertThat(typeMapperFactory.of("DATE").sqlInputRead(), equalTo("new Date(stream.readTimestamp().getTime())"));
        assertThat(typeMapperFactory.of("DATE").sqlOutputWrite(), equalTo("stream.writeTimestamp(new java.sql.Timestamp(this.getField().getTime()));"));
        assertThat(typeMapperFactory.of("DATE").methodReturnStatement(), equalTo("return new Date( ((java.sql.Timestamp) resultSet.getObject(1)).getTime() );"));
    }

    @Test
    public void knowsAboutCustomType() {
        assertThat(typeMapperFactory.of("any_type").functionParameterSettingStatement(), equalTo("statement.setObject(index, field);"));
        assertThat(typeMapperFactory.of("any_type").javaType(), equalTo("AnyType"));
        assertThat(typeMapperFactory.of("any_type").sqlInputRead(), equalTo("stream.readObject()"));
        assertThat(typeMapperFactory.of("any_type").sqlOutputWrite(), equalTo("stream.writeObject(this.getField());"));
        assertThat(typeMapperFactory.of("any_type").methodReturnStatement(), equalTo("return (AnyType) resultSet.getObject(1);"));
    }

    @Test
    public void knowsAboutArrayType() {
        assertThat(typeMapperFactory.of("any_table_type").functionParameterSettingStatement(), equalTo("statement.setObject(index, field);"));
        assertThat(typeMapperFactory.of("any_table_type").javaType(), equalTo("AnyTableType"));
        assertThat(typeMapperFactory.of("any_table_type").sqlInputRead(), equalTo("stream.readObject()"));
        assertThat(typeMapperFactory.of("any_table_type").sqlOutputWrite(), equalTo("stream.writeObject(this.getField());"));
        assertThat(typeMapperFactory.of("any_table_type").methodReturnStatement(), equalTo("return AnyTableType.with((Object[]) resultSet.getArray(1).getArray());"));
    }
}
