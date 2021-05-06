package ericminio.javaoracle.data;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static ericminio.javaoracle.data.Query.with;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class DatabaseTestReadyTest extends DatabaseTest {

    @Before
    public void createTypes() throws IOException {
        executeFromResource("create-types.sql");
        executeFromResource("create-package.sql");
    }

    @Test
    public void createdType() {
        Integer count = with(connection).selectInt("select count(1) from user_types");

        assertThat(count, equalTo(2));
    }

    @Test
    public void createdPackage() {
        Integer count = with(connection).selectInt("select count(1) from user_objects where object_type='PACKAGE'");

        assertThat(count, equalTo(1));
    }
}
