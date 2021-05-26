package ericminio.javaoracle.demos.cursor;

import ericminio.javaoracle.data.DatabaseTest;
import org.junit.Before;
import org.junit.Test;

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
    public void expectedRowCount() throws SQLException {
        ReturningCursor returningCursor = new ReturningCursor(connection);
        ResultSet resultSet = returningCursor.getProducts();
        int count = 0;
        while (resultSet.next()) {
            count ++;
        }
        assertThat(count, equalTo(2));
    }

    @Test
    public void expectedContent() throws SQLException {
        ReturningCursor returningCursor = new ReturningCursor(connection);
        ResultSet resultSet = returningCursor.getProducts();

        resultSet.next();
        assertThat(resultSet.getString(1), equalTo("one"));
        resultSet.next();
        assertThat(resultSet.getString(1), equalTo("two"));
    }
}
