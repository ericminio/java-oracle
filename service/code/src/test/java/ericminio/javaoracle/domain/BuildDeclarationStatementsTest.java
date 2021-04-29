package ericminio.javaoracle.domain;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class BuildDeclarationStatementsTest {

    @Test
    public void numberType() {
        Parameters parameters = new Parameters();
        parameters.add("any_field number");
        assertThat(new BuildDeclarationStatements().please(parameters), equalTo("" +
                "    private BigDecimal anyField;"
        ));
    }

    @Test
    public void stringType() {
        Parameters parameters = new Parameters();
        parameters.add("field1 varchar2(10)");
        assertThat(new BuildDeclarationStatements().please(parameters), equalTo("" +
                "    private String field1;"
        ));
    }

    @Test
    public void twoEntries() {
        Parameters parameters = new Parameters();
        parameters.add("field1 varchar2(10)");
        parameters.add("field2 number");
        assertThat(new BuildDeclarationStatements().please(parameters), equalTo("" +
                "    private String field1;\n" +
                "    private BigDecimal field2;"
        ));
    }
}
