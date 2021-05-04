package ericminio.javaoracle.domain;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class BuildWriteSqlTest {

    @Test
    public void forNumber() {
        Parameters parameters = new Parameters();
        parameters.add("any_field number");
        assertThat(new BuildWriteSql(new TypeMapperFactory()).please(parameters), equalTo("" +
                "        stream.writeBigDecimal(this.getAnyField());"
        ));
    }

    @Test
    public void forString() {
        Parameters parameters = new Parameters();
        parameters.add("any_field varchar2(10)");
        assertThat(new BuildWriteSql(new TypeMapperFactory()).please(parameters), equalTo("" +
                "        stream.writeString(this.getAnyField());"
        ));
    }

    @Test
    public void forDate() {
        Parameters parameters = new Parameters();
        parameters.add("any_field date");
        assertThat(new BuildWriteSql(new TypeMapperFactory()).please(parameters), equalTo("" +
                "        stream.writeTimestamp(new java.sql.Timestamp(this.getAnyField().getTime()));"
        ));
    }

    @Test
    public void forCustomType() {
        Parameters parameters = new Parameters();
        parameters.add("any_field custom_type");
        assertThat(new BuildWriteSql(new TypeMapperFactory()).please(parameters), equalTo("" +
                "        stream.writeObject(this.getAnyField());"
        ));
    }

    @Test
    public void twoEntries() {
        Parameters parameters = new Parameters();
        parameters.add("field1 varchar2(10)");
        parameters.add("field2 number(15,4)");
        assertThat(new BuildWriteSql(new TypeMapperFactory()).please(parameters), equalTo("" +
                "        stream.writeString(this.getField1());\n" +
                "        stream.writeBigDecimal(this.getField2());"
        ));
    }

    @Test
    public void ignoreArrayTypeForTheMoment() {
        List<List<String>> typeSpecifications = Arrays.asList(
                Arrays.asList(
                        "create or replace type record_type as object(\n",
                        "   value number\n",
                        ");\n"),
                Arrays.asList("create or replace type array_type as table of record_type;")
        );
        TypeMapperFactory typeMapperFactory = new TypeMapperFactory(typeSpecifications);
        Parameters parameters = new Parameters();
        parameters.add("any_field array_type");
        assertThat(new BuildWriteSql(typeMapperFactory).please(parameters), equalTo("" +
                "        // ignore anyField"
        ));
    }
}
