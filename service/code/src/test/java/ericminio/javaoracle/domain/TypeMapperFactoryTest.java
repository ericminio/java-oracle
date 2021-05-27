package ericminio.javaoracle.domain;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertThat;

public class TypeMapperFactoryTest {

    private TypeMapperFactory typeMapperFactory;

    @Before
    public void initTypes() {
        List<List<String>> typeSpecifications = Arrays.asList(
                Arrays.asList("create or replace type any_type as object (value number);"),
                Arrays.asList("create or replace type another_type as object (value number);"),
                Arrays.asList("create or replace type any_table_type as table of any_type;"),
                Arrays.asList("create or replace type any_varray_type as varray(15) of any_type;"),
                Arrays.asList("create or replace type missing_semicolon as table of any_type"),
                Arrays.asList("create or replace type extra_spaces as table of any_type    "),
                Arrays.asList("create or replace type any_type_nesting as object (one any_type, two another_type, three number)"),
                Arrays.asList("create or replace type \"quoted_type\" as object (one any_type)"),
                Arrays.asList("TYPE \"RANDOM_case\" AS varray(1500) OF ANY_type"),
                Arrays.asList("TYPE \"multiline_array\" IS varray(1500)\n", "OF ANY_type;"),
                Arrays.asList("TYPE \"extra_inline_spaces\"         AS varray(1500) OF ANY_type"),
                Arrays.asList("TYPE \"missing_OF_leading_space\" AS varray(1500)OF ANY_type")
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
    public void isArrayTypeResistsUpperCaseIncoming() {
        assertThat(typeMapperFactory.isArrayType("ANY_TABLE_TYPE"), equalTo(true));
    }

    @Test
    public void isArrayTypeResistsQuotes() {
        assertThat(typeMapperFactory.isArrayType("random_case"), equalTo(true));
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

    @Test
    public void recordTypeGetterResistsMissingSemicolon() {
        assertThat(typeMapperFactory.recordTypeOfArrayType("missing_semicolon"), equalTo("any_type"));
    }

    @Test
    public void recordTypeGetterResistsExtraSpaces() {
        assertThat(typeMapperFactory.recordTypeOfArrayType("extra_spaces"), equalTo("any_type"));
    }

    @Test
    public void recordTypeGetterResistsUpperCase() {
        assertThat(typeMapperFactory.recordTypeOfArrayType("random_case"), equalTo("any_type"));
    }

    @Test
    public void recordTypeGetterResistsQuotedType() {
        assertThat(typeMapperFactory.recordTypeOfArrayType("EXTRA_SPACES"), equalTo("any_type"));
    }

    @Test
    public void knowsAboutNumber() {
        assertThat(typeMapperFactory.of("number").functionParameterSettingStatement(), equalTo("statement.setBigDecimal(index, field);"));
        assertThat(typeMapperFactory.of("number").javaType(), equalTo("BigDecimal"));
        assertThat(typeMapperFactory.of("number").sqlInputRead(), equalTo("stream.readBigDecimal()"));
        assertThat(typeMapperFactory.of("number").sqlOutputWrite(), equalTo("stream.writeBigDecimal(this.getField());"));
        assertThat(typeMapperFactory.of("number").methodReturnStatement(), equalTo("return (BigDecimal) data;"));
    }

    @Test
    public void knowsAboutVarchar2() {
        assertThat(typeMapperFactory.of("VARCHAR2").functionParameterSettingStatement(), equalTo("statement.setString(index, field);"));
        assertThat(typeMapperFactory.of("VARCHAR2").javaType(), equalTo("String"));
        assertThat(typeMapperFactory.of("VARCHAR2").sqlInputRead(), equalTo("stream.readString()"));
        assertThat(typeMapperFactory.of("VARCHAR2").sqlOutputWrite(), equalTo("stream.writeString(this.getField());"));
        assertThat(typeMapperFactory.of("VARCHAR2").methodReturnStatement(), equalTo("return (String) data;"));
    }

    @Test
    public void knowsAboutVarchar() {
        assertThat(typeMapperFactory.of("VARCHAR").functionParameterSettingStatement(), equalTo("statement.setString(index, field);"));
        assertThat(typeMapperFactory.of("VARCHAR").javaType(), equalTo("String"));
        assertThat(typeMapperFactory.of("VARCHAR").sqlInputRead(), equalTo("stream.readString()"));
        assertThat(typeMapperFactory.of("VARCHAR").sqlOutputWrite(), equalTo("stream.writeString(this.getField());"));
        assertThat(typeMapperFactory.of("VARCHAR").methodReturnStatement(), equalTo("return (String) data;"));
    }

    @Test
    public void knowsAboutDate() {
        assertThat(typeMapperFactory.of("DATE").functionParameterSettingStatement(), equalTo("statement.setTimestamp(index, field == null ? null : new java.sql.Timestamp(field.getTime()));"));
        assertThat(typeMapperFactory.of("DATE").javaType(), equalTo("Date"));
        assertThat(typeMapperFactory.of("DATE").sqlInputRead(), equalTo("buildDateOrNull(stream.readTimestamp())"));
        assertThat(typeMapperFactory.of("DATE").sqlOutputWrite(), equalTo("stream.writeTimestamp(new java.sql.Timestamp(this.getField().getTime()));"));
        assertThat(typeMapperFactory.of("DATE").methodReturnStatement(), equalTo("return data == null ? null : new Date( ((java.sql.Timestamp) data).getTime() );"));
    }

    @Test
    public void knowsAboutCustomType() {
        assertThat(typeMapperFactory.of("any_type").functionParameterSettingStatement(), equalTo("statement.setObject(index, field);"));
        assertThat(typeMapperFactory.of("any_type").javaType(), equalTo("AnyType"));
        assertThat(typeMapperFactory.of("any_type").sqlInputRead(), equalTo("(AnyType) stream.readObject()"));
        assertThat(typeMapperFactory.of("any_type").sqlOutputWrite(), equalTo("stream.writeObject(this.getField());"));
        assertThat(typeMapperFactory.of("any_type").methodReturnStatement(), equalTo("return (AnyType) data;"));
    }

    @Test
    public void knowsAboutArrayType() {
        assertThat(typeMapperFactory.of("any_table_type").functionParameterSettingStatement(), equalTo("statement.setObject(index, field);"));
        assertThat(typeMapperFactory.of("any_table_type").javaType(), equalTo("AnyTableType"));
        assertThat(typeMapperFactory.of("any_table_type").sqlInputRead(), equalTo("AnyTableType.with((Object[]) stream.readArray().getArray())"));
        assertThat(typeMapperFactory.of("any_table_type").sqlOutputWrite(), equalTo("stream.writeObject(this.getField());"));
        assertThat(typeMapperFactory.of("any_table_type").methodReturnStatement(), equalTo("return AnyTableType.with((Object[]) ((java.sql.Array) data).getArray());"));
    }

    @Test
    public void customTypesOfFieldIsEmptyWhenTypeHasNoCustomTypeField() {
        assertThat(typeMapperFactory.customTypesOfFields("any_type").size(), equalTo(0));
    }

    @Test
    public void customTypesOfFieldIgnoresNativeType() {
        assertThat(typeMapperFactory.customTypesOfFields("any_type_nesting").size(), equalTo(2));
        assertThat(typeMapperFactory.customTypesOfFields("any_type_nesting").get(0), equalTo("any_type"));
        assertThat(typeMapperFactory.customTypesOfFields("any_type_nesting").get(1), equalTo("another_type"));
    }

    @Test
    public void customTypesOfFieldResistsQuotedType() {
        assertThat(typeMapperFactory.customTypesOfFields("quoted_type").size(), equalTo(1));
        assertThat(typeMapperFactory.customTypesOfFields("quoted_type").get(0), equalTo("any_type"));
    }

    @Test
    public void customTypesOfFieldResistsVarray() {
        assertThat(typeMapperFactory.customTypesOfFields("random_case").size(), equalTo(0));
    }

    @Test
    public void recordTypeGetterResistsMultilineDefinition() {
        assertThat(typeMapperFactory.recordTypeOfArrayType("multiline_array"), equalTo("any_type"));
    }

    @Test
    public void isArrayTypeResistsMultilineDefinition() {
        assertThat(typeMapperFactory.isArrayType("multiline_array"), equalTo(true));
    }

    @Test
    public void recordTypeGetterResistsExtraInlineSpaces() {
        assertThat(typeMapperFactory.recordTypeOfArrayType("extra_inline_spaces"), equalTo("any_type"));
    }

    @Test
    public void isArrayTypeResistsExtraInlineSpaces() {
        assertThat(typeMapperFactory.isArrayType("extra_inline_spaces"), equalTo(true));
    }

    @Test
    public void recordTypeGetterResistsMissingOfLeadingSpace() {
        assertThat(typeMapperFactory.recordTypeOfArrayType("missing_OF_leading_space"), equalTo("any_type"));
    }

    @Test
    public void isArrayTypeResistsMissingOfLeadingSpace() {
        assertThat(typeMapperFactory.isArrayType("missing_OF_leading_space"), equalTo(true));
    }

    @Test
    public void refCursorIsNotAnArrayType() {
        assertThat(new TypeMapperFactory(Arrays.asList(
                Arrays.asList("type example_cursor is ref cursor;")
        )).isArrayType("example_cursor"), equalTo(false));
    }

    @Test
    public void cursorTypeCanBeDetected() {
        assertThat(new TypeMapperFactory(Arrays.asList(
                Arrays.asList("type example_cursor is ref cursor;")
        )).isCursorType("example_cursor"), equalTo(true));
    }

    @Test
    public void knowsAboutCursorType() {
        TypeMapperFactory typeMapperFactory = new TypeMapperFactory(Arrays.asList(
                Arrays.asList("type example_cursor is ref cursor;")
        ));
        assertThat(typeMapperFactory.of("example_cursor"), instanceOf(TypeMapperCursorType.class));
        assertThat(typeMapperFactory.of("example_cursor").functionParameterSettingStatement(), equalTo("statement.setObject(index, field);"));
        assertThat(typeMapperFactory.of("example_cursor").javaType(), equalTo("ResultSet"));
        assertThat(typeMapperFactory.of("example_cursor").sqlInputRead(), equalTo("stream.readObject()"));
        assertThat(typeMapperFactory.of("example_cursor").sqlOutputWrite(), equalTo("stream.writeObject(this.getField());"));
        assertThat(typeMapperFactory.of("example_cursor").methodReturnStatement(), equalTo("return (ResultSet) data;"));
    }

    @Test
    public void knowsAboutClobType() {
        assertThat(typeMapperFactory.of("clob"), instanceOf(TypeMapperClobType.class));
        assertThat(typeMapperFactory.of("clob").functionParameterSettingStatement(), equalTo("statement.setClob(index, field);"));
        assertThat(typeMapperFactory.of("clob").javaType(), equalTo("Clob"));
        assertThat(typeMapperFactory.of("clob").sqlInputRead(), equalTo("stream.readClob()"));
        assertThat(typeMapperFactory.of("clob").sqlOutputWrite(), equalTo("stream.writeClob(this.getField());"));
        assertThat(typeMapperFactory.of("clob").methodReturnStatement(), equalTo("return (Clob) data;"));
    }

}
