package ericminio.javaoracle.domain;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class BuildReadSqlTest {

    @Test
    public void integerType() {
        Parameters parameters = new Parameters();
        parameters.add("any_field number");
        assertThat(new BuildReadSql().please(parameters), equalTo("" +
                "        this.setAnyField(stream.readBigDecimal());"
        ));
    }

    @Test
    public void stringType() {
        Parameters parameters = new Parameters();
        parameters.add("field varchar2(10)");
        assertThat(new BuildReadSql().please(parameters), equalTo("" +
                "        this.setField(stream.readString());"
        ));
    }

    @Test
    public void twoEntries() {
        Parameters parameters = new Parameters();
        parameters.add("field1 varchar2(10)");
        parameters.add("field2 number(15,4)");
        assertThat(new BuildReadSql().please(parameters), equalTo("" +
                "        this.setField1(stream.readString());\n" +
                "        this.setField2(stream.readBigDecimal());"
        ));
    }
}
