package ericminio.javaoracle.demos;

import ericminio.javaoracle.data.DatabaseTest;
import org.junit.Before;
import org.junit.Test;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static ericminio.javaoracle.data.Query.with;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ReturningCursorTest extends DatabaseTest {

    @Before
    public void seeds() {
        with(connection).execute("create table product(name varchar2(15))");
        with(connection).execute("insert into product (name) values ('one')");
        with(connection).execute("insert into product (name) values ('two')");
        with(connection).execute("CREATE OR REPLACE PACKAGE returning_cursor\n" +
                "AS\n" +
                "\n" +
                "    TYPE product_cursor IS REF CURSOR;\n" +
                "    FUNCTION get_products RETURN product_cursor;\n" +
                "\n" +
                "END returning_cursor;");
        with(connection).execute("create or replace package body returning_cursor\n" +
                "AS\n" +
                "\n" +
                "    function get_products return product_cursor\n" +
                "    as\n" +
                "       rc product_cursor;\n" +
                "    begin\n" +
                "        open rc for select name from product;\n" +
                "        return rc;\n" +
                "    end;\n" +
                "\n" +
                "END returning_cursor;");
    }

    @Test
    public void exploration() throws SQLException {
        PreparedStatement statement = connection.prepareStatement("select returning_cursor.get_products() from dual");
        ResultSet resultSet = statement.executeQuery();
        resultSet.next();
        Object data = resultSet.getObject(1);

        ResultSet dunno = (ResultSet) data;
        int count = 0;
        while (dunno.next()) {
            count ++;
        }
        assertThat(count, equalTo(2));
    }
}
