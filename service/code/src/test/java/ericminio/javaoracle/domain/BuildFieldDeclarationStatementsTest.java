package ericminio.javaoracle.domain;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class BuildFieldDeclarationStatementsTest {

    @Test
    public void integerType() {
        Parameters parameters = new Parameters();
        parameters.add("any_field integer");
        assertThat(new BuildFieldDeclarationStatements().please(parameters), equalTo("" +
                "    private Integer anyField;"
        ));
    }

    @Test
    public void stringType() {
        Parameters parameters = new Parameters();
        parameters.add("field1 varchar2(10)");
        assertThat(new BuildFieldDeclarationStatements().please(parameters), equalTo("" +
                "    private String field1;"
        ));
    }

    @Test
    public void twoEntries() {
        Parameters parameters = new Parameters();
        parameters.add("field1 varchar2(10)");
        parameters.add("field2 integer");
        assertThat(new BuildFieldDeclarationStatements().please(parameters), equalTo("" +
                "    private String field1;\n" +
                "    private Integer field2;"
        ));
    }
}
