package ericminio.javaoracle.data;

import ericminio.javaoracle.domain.ExtractPackageName;
import ericminio.javaoracle.domain.ExtractTypeName;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class GetDataFromDatabaseTest extends DatabaseTest {

    Incoming incoming;

    @Before
    public void readFiles() throws IOException, SQLException {
        executeFromResource("create-types.sql");
        executeFromResource("create-package.sql");
        incoming = new GetDataFromDatabase().please("any_package", "example_");
    }

    @Test
    public void exposesPackageSpecification() {
        List<String> specification = incoming.getPackageSpecification();

        assertThat(new ExtractPackageName().please(specification), equalTo("any_package"));
    }

    @Test
    public void exposesTypeSpecifications() {
        List<List<String>> specifications = incoming.getTypeSpecifications();
        assertThat(specifications.size(), equalTo(2));
        assertThat(new ExtractTypeName().please(specifications.get(0)), equalTo("example_any_type"));
        assertThat(new ExtractTypeName().please(specifications.get(1)), equalTo("example_array_type"));
    }

    @Test
    public void exposesTypeNames() {
        List<String> names = incoming.getTypeNames();
        assertThat(names.size(), equalTo(2));
        assertThat(names.get(0), equalTo("EXAMPLE_ANY_TYPE"));
        assertThat(names.get(1), equalTo("EXAMPLE_ARRAY_TYPE"));
    }
}
