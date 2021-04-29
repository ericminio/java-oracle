package ericminio.javaoracle.domain;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class BuildFieldAccessorsTest {

    @Test
    public void one() {
        Parameters parameters = new Parameters();
        parameters.add("any_field integer");
        assertThat(new BuildFieldAccessors().please(parameters), equalTo("" +
                "    public Integer getAnyField() {\n" +
                "        return this.anyField;\n" +
                "    }\n" +
                "    public void setAnyField(Integer anyField) {\n" +
                "        this.anyField = anyField;\n" +
                "    }"
        ));
    }

    @Test
    public void two() {
        Parameters parameters = new Parameters();
        parameters.add("any_field integer");
        parameters.add("another_field integer");
        assertThat(new BuildFieldAccessors().please(parameters), equalTo("" +
                "    public Integer getAnyField() {\n" +
                "        return this.anyField;\n" +
                "    }\n" +
                "    public void setAnyField(Integer anyField) {\n" +
                "        this.anyField = anyField;\n" +
                "    }\n" +
                "\n" +
                "    public Integer getAnotherField() {\n" +
                "        return this.anotherField;\n" +
                "    }\n" +
                "    public void setAnotherField(Integer anotherField) {\n" +
                "        this.anotherField = anotherField;\n" +
                "    }"
        ));
    }
}
