package ericminio.javaoracle.demos;

import ericminio.javaoracle.support.DatabaseTest;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import static ericminio.javaoracle.data.Query.with;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class FunctionReturningComplexTypeTest extends DatabaseTest {

    @Before
    public void createStructure() {
        with(connection).executeIgnoringFailure("drop package body returning_complex_type");
        with(connection).executeIgnoringFailure("drop package returning_complex_type");
        with(connection).executeIgnoringFailure("drop type array_of_custom_type");
        with(connection).executeIgnoringFailure("drop type complex_type");
        with(connection).executeIgnoringFailure("drop type custom_type");

        with(connection).execute("" +
                "create type custom_type as object" +
                "(" +
                "   id number,\n" +
                "   label varchar2(15),\n" +
                "   creation_date date\n" +
                ");");
        with(connection).execute("" +
                "create type complex_type as object" +
                "(" +
                "   field custom_type\n" +
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
                "    begin\n" +
                "        return complex_type(custom_type(15, 'hello', to_date('2015/01/15 19:15:42', 'YYYY/MM/DD HH24:MI:SS')));\n" +
                "    end;\n" +
                "\n" +
                "END returning_complex_type;");
    }

    @Test
    public void complexTypeCanBeFetched() throws SQLException, ParseException {
        FunctionReturningComplexType functionReturningComplexType = new FunctionReturningComplexType(connection);
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy/M/dd hh:mm:ss");
        CustomType customType = new CustomType();
        customType.setId(new BigDecimal(15));
        customType.setLabel("hello");
        customType.setCreationDate(dateformat.parse("2015/01/15 19:15:42"));
        ComplexType complexType = new ComplexType();
        complexType.setValue(customType);

        assertThat(functionReturningComplexType.getField(), equalTo(complexType));
    }
}
