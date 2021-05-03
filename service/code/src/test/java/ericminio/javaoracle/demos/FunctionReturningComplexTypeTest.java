package ericminio.javaoracle.demos;

import ericminio.javaoracle.support.DatabaseTest;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.ParseException;

import static ericminio.javaoracle.data.Query.with;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class FunctionReturningComplexTypeTest extends DatabaseTest {

    @Before
    public void createStructure() {
        with(connection).execute("create or replace type custom_type as object(id number, label varchar2(15), creation_date date);");
        with(connection).execute("create or replace type array_of_custom_type as varray(15) of custom_type;");
        with(connection).execute("" +
                "create type complex_type as object" +
                "(" +
                "   value custom_type,\n" +
                "   collection array_of_custom_type\n" +
                ");");
        with(connection).execute("" +
                "create or replace package returning_complex_type\n" +
                "AS\n" +
                "    FUNCTION get_field RETURN complex_type;\n" +
                "\n" +
                "END returning_complex_type;");
        with(connection).execute("" +
                "create or replace package body returning_complex_type\n" +
                "AS\n" +
                "\n" +
                "    function get_field return complex_type\n" +
                "    as\n" +
                "       R array_of_custom_type;\n" +
                "    begin\n" +
                "        R := array_of_custom_type();\n" +
                "        R.extend;\n" +
                "        R(1) := custom_type(1, 'one', to_date('2001/01/01 01:01:01', 'YYYY/MM/DD HH24:MI:SS'));\n" +
                "        R.extend;\n" +
                "        R(2) := custom_type(2, 'two', to_date('2002/02/02 02:02:02', 'YYYY/MM/DD HH24:MI:SS'));\n" +
                "        return complex_type(custom_type(15, 'hello', to_date('2015/01/15 19:15:42', 'YYYY/MM/DD HH24:MI:SS')), R);\n" +
                "    end;\n" +
                "\n" +
                "END returning_complex_type;");
    }

    @Test
    public void nestedCustomTypeISAvailable() throws SQLException, ParseException {
        FunctionReturningComplexType functionReturningComplexType = new FunctionReturningComplexType(connection);
        CustomType customType = new CustomType();
        customType.setId(new BigDecimal(15));
        customType.setLabel("hello");
        customType.setCreationDate(dateformat.parse("2015/01/15 19:15:42"));

        assertThat(functionReturningComplexType.getField().getValue(), equalTo(customType));
    }

    @Test
    public void nestedArrayOfCustomTypeIsAvailable() throws SQLException, ParseException {
        FunctionReturningComplexType functionReturningComplexType = new FunctionReturningComplexType(connection);
        ComplexType field = functionReturningComplexType.getField();

        assertThat(field.getCollection().length(), equalTo(2));
        assertThat(field.getCollection().getElement(0).getLabel(), equalTo("one"));
        assertThat(field.getCollection().getElement(1).getLabel(), equalTo("two"));
    }

    @Test
    public void exploreComplexTypeComparison() throws SQLException, ParseException {
        FunctionReturningComplexType functionReturningComplexType = new FunctionReturningComplexType(connection);
        ComplexType complexType = new ComplexType();

        CustomType customType = new CustomType();
        customType.setId(new BigDecimal(15));
        customType.setLabel("hello");
        customType.setCreationDate(dateformat.parse("2015/01/15 19:15:42"));
        complexType.setValue(customType);

        CustomType one = new CustomType();
        one.setId(new BigDecimal(1));
        one.setLabel("one");
        one.setCreationDate(dateformat.parse("2001/01/01 01:01:01"));
        CustomType two = new CustomType();
        two.setId(new BigDecimal(2));
        two.setLabel("two");
        two.setCreationDate(dateformat.parse("2002/02/02 02:02:02"));
        complexType.setCollection(ArrayOfCustomType.with(new CustomType[] { one, two }));

        assertThat(functionReturningComplexType.getField(), equalTo(complexType));
    }
}
