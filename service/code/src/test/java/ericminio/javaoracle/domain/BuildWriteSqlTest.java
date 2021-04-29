package ericminio.javaoracle.domain;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class BuildWriteSqlTest {

    @Test
    public void integerType() {
        Parameters parameters = new Parameters();
        parameters.add("any_field integer");
        assertThat(new BuildWriteSql().please(parameters), equalTo("" +
                "        stream.writeInt(this.getAnyField());"
        ));
    }

    @Test
    public void stringType() {
        Parameters parameters = new Parameters();
        parameters.add("field varchar2(10)");
        assertThat(new BuildWriteSql().please(parameters), equalTo("" +
                "        stream.writeString(this.getField());"
        ));
    }

    @Test
    public void twoEntries() {
        Parameters parameters = new Parameters();
        parameters.add("field1 varchar2(10)");
        parameters.add("field2 integer");
        assertThat(new BuildWriteSql().please(parameters), equalTo("" +
                "        stream.writeString(this.getField1());\n" +
                "        stream.writeInt(this.getField2());"
        ));
    }
}
