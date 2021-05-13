package ericminio.javaoracle.domain;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class BuildDeclarationStatementsTest {

    private BuildDeclarationStatements buildDeclarationStatements;

    @Before
    public void sut() {
        buildDeclarationStatements = new BuildDeclarationStatements(new TypeMapperFactory());
    }

    @Test
    public void numberType() {
        Parameters parameters = new Parameters();
        parameters.add("any_field number");
        assertThat(buildDeclarationStatements.please(parameters), equalTo("" +
                "    private BigDecimal anyField;"
        ));
    }

    @Test
    public void stringType() {
        Parameters parameters = new Parameters();
        parameters.add("field1 varchar2(10)");
        assertThat(buildDeclarationStatements.please(parameters), equalTo("" +
                "    private String field1;"
        ));
    }

    @Test
    public void twoEntries() {
        Parameters parameters = new Parameters();
        parameters.add("field1 varchar2(10)");
        parameters.add("field2 number");
        assertThat(buildDeclarationStatements.please(parameters), equalTo("" +
                "    private String field1;\n" +
                "    private BigDecimal field2;"
        ));
    }
}
