package ericminio.javaoracle.domain;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class BuildReadSqlTest {

    BuildReadSql buildReadSql;
    
    @Before
    public void sut() {
        List<List<String>> typeSpecifications = Arrays.asList(
                Arrays.asList("create type custom_type as object(value number)")
        );
        TypeMapperFactory typeMapperFactory = new TypeMapperFactory(typeSpecifications);
        buildReadSql = new BuildReadSql(typeMapperFactory);
    }
    
    @Test
    public void forNumber() {
        Parameters parameters = new Parameters();
        parameters.add("any_field number");
        assertThat(buildReadSql.please(parameters), equalTo("" +
                "        this.setAnyField(stream.readBigDecimal());"
        ));
    }

    @Test
    public void forString() {
        Parameters parameters = new Parameters();
        parameters.add("field varchar2(10)");
        assertThat(buildReadSql.please(parameters), equalTo("" +
                "        this.setField(stream.readString());"
        ));
    }

    @Test
    public void forDate() {
        Parameters parameters = new Parameters();
        parameters.add("field date");
        assertThat(buildReadSql.please(parameters), equalTo("" +
                "        this.setField(buildDateOrNull(stream.readTimestamp()));"
        ));
    }

    @Test
    public void forClob() {
        Parameters parameters = new Parameters();
        parameters.add("field clob");
        assertThat(buildReadSql.please(parameters), equalTo("" +
                "        this.setField(stream.readClob());"
        ));
    }

    @Test
    public void forCustomType() {
        Parameters parameters = new Parameters();
        parameters.add("field custom_type");
        assertThat(buildReadSql.please(parameters), equalTo("" +
                "        this.setField((CustomType) stream.readObject());"
        ));
    }

    @Test
    public void twoEntries() {
        Parameters parameters = new Parameters();
        parameters.add("field1 varchar2(10)");
        parameters.add("field2 number(15,4)");
        assertThat(buildReadSql.please(parameters), equalTo("" +
                "        this.setField1(stream.readString());\n" +
                "        this.setField2(stream.readBigDecimal());"
        ));
    }

    @Test
    public void forArrayTypeWeNeedTheTypeSpecifications() {
        List<List<String>> typeSpecifications = Arrays.asList(
                Arrays.asList(
                        "create or replace type record_type as object(\n",
                        "   value number\n",
                        ");\n"),
                Arrays.asList("create or replace type array_type as table of record_type;")
        );
        TypeMapperFactory typeMapperFactory = new TypeMapperFactory(typeSpecifications);
        Parameters parameters = new Parameters();
        parameters.add("field array_type");
        assertThat(new BuildReadSql(typeMapperFactory).please(parameters), equalTo("" +
                "        this.setField(ArrayType.with((Object[]) stream.readArray().getArray()));"
        ));
    }
}
