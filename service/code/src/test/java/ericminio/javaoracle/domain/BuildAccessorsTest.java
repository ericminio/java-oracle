package ericminio.javaoracle.domain;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class BuildAccessorsTest {

    BuildAccessors buildAccessors;
    
    @Before
    public void sut() {
        buildAccessors = new BuildAccessors(new TypeMapperFactory());
    }
    
    @Test
    public void one() {
        Parameters parameters = new Parameters();
        parameters.add("any_field number");
        assertThat(buildAccessors.please(parameters), equalTo("" +
                "    public BigDecimal getAnyField() {\n" +
                "        return this.anyField;\n" +
                "    }\n" +
                "    public void setAnyField(BigDecimal anyField) {\n" +
                "        this.anyField = anyField;\n" +
                "    }"
        ));
    }

    @Test
    public void two() {
        Parameters parameters = new Parameters();
        parameters.add("any_field number");
        parameters.add("another_field number");
        assertThat(buildAccessors.please(parameters), equalTo("" +
                "    public BigDecimal getAnyField() {\n" +
                "        return this.anyField;\n" +
                "    }\n" +
                "    public void setAnyField(BigDecimal anyField) {\n" +
                "        this.anyField = anyField;\n" +
                "    }\n" +
                "\n" +
                "    public BigDecimal getAnotherField() {\n" +
                "        return this.anotherField;\n" +
                "    }\n" +
                "    public void setAnotherField(BigDecimal anotherField) {\n" +
                "        this.anotherField = anotherField;\n" +
                "    }"
        ));
    }
}
