package ericminio.javaoracle.domain;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class BuildReadSqlTest {

    @Test
    public void forNumber() {
        Parameters parameters = new Parameters();
        parameters.add("any_field number");
        assertThat(new BuildReadSql().please(parameters), equalTo("" +
                "        this.setAnyField(stream.readBigDecimal());"
        ));
    }

    @Test
    public void forString() {
        Parameters parameters = new Parameters();
        parameters.add("field varchar2(10)");
        assertThat(new BuildReadSql().please(parameters), equalTo("" +
                "        this.setField(stream.readString());"
        ));
    }

    @Test
    public void forDate() {
        Parameters parameters = new Parameters();
        parameters.add("field date");
        assertThat(new BuildReadSql().please(parameters), equalTo("" +
                "        this.setField(new Date(stream.readTimestamp().getTime()));"
        ));
    }

    @Test
    public void forCustomType() {
        Parameters parameters = new Parameters();
        parameters.add("field custom_type");
        assertThat(new BuildReadSql().please(parameters), equalTo("" +
                "        this.setField(stream.readObject());"
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
