package ericminio.javaoracle.domain;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class BuildFieldAccessorsTest {

    @Test
    public void integerType() {
        Parameters parameters = new Parameters();
        parameters.add("any_field integer");
        assertThat(new BuildFieldAccessors().please(parameters), equalTo("" +
                "    public Integer getAnyField() {\n" +
                "        return this.anyField;\n" +
                "    }\n" +
                "    public void setAnyField(Integer anyField) {\n" +
                "        this.anyField = anyField;\n" +
                "    }\n"
        ));
    }

    @Test
    public void stringType() {
        Parameters parameters = new Parameters();
        parameters.add("field varchar2(15)");
        assertThat(new BuildFieldAccessors().please(parameters), equalTo("" +
                "    public String getField() {\n" +
                "        return this.field;\n" +
                "    }\n" +
                "    public void setField(String field) {\n" +
                "        this.field = field;\n" +
                "    }\n"
        ));
    }
}
