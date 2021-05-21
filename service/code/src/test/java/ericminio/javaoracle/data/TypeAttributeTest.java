package ericminio.javaoracle.data;

import ericminio.javaoracle.domain.ExtractFunctionSpecifications;
import ericminio.javaoracle.domain.Incoming;
import ericminio.javaoracle.domain.JoinWith;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import static ericminio.javaoracle.data.Query.with;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class TypeAttributeTest extends DatabaseTest {

    Incoming incoming;

    @Before
    public void structure() throws IOException {
        with(connection).execute("create table product(id number, name varchar2(15))");
        with(connection).execute("" +
                "create or replace package any_package as\n" +
                "   function any_function(input product.id%TYPE) return product.name%TYPE;\n" +
                "end any_package;");
    }

    @Test
    public void translatesTypeAttribute() throws SQLException {
        incoming = new GetDataFromDatabase().please("any_package", "example_");
        List<String> packageSpecification = incoming.getPackageSpecification();
        List<List<String>> functionSpecifications = new ExtractFunctionSpecifications().please(packageSpecification);
        List<String> functionSpecification = functionSpecifications.get(0);
        String statement = new JoinWith("").please(functionSpecification);

        assertThat(statement, equalTo("function any_function(input NUMBER) return VARCHAR2;"));
    }
}
