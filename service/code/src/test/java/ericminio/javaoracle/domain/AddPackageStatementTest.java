package ericminio.javaoracle.domain;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.startsWith;
import static org.hamcrest.MatcherAssert.assertThat;

public class AddPackageStatementTest {

    @Test
    public void addsGivenPackageName() {
        assertThat(new AddPackageStatement("any.name").to("any code"), startsWith("package any.name;\n"));
    }

    @Test
    public void insertsEmptyLine() {
        assertThat(new AddPackageStatement("any.name").to("any code"), equalTo("package any.name;\n\nany code"));
    }
}
