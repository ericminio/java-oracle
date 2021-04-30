package ericminio.javaoracle.domain;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class BuildSqlStatementParameterSettingsTest {

    @Test
    public void forString() {
        Parameters parameters = new Parameters();
        parameters.add("field varchar2");
        assertThat(new BuildSqlStatementParameterSettings().please(parameters), equalTo("" +
                "        statement.setString(1, field);\n" +
                ""));
    }

    @Test
    public void forNumber() {
        Parameters parameters = new Parameters();
        parameters.add("field number");
        assertThat(new BuildSqlStatementParameterSettings().please(parameters), equalTo("" +
                "        statement.setBigDecimal(1, field);\n" +
                ""));
    }

    @Test
    public void forDate() {
        Parameters parameters = new Parameters();
        parameters.add("field date");
        assertThat(new BuildSqlStatementParameterSettings().please(parameters), equalTo("" +
                "        statement.setTimestamp(1, new java.sql.Timestamp(field.getTime()));\n" +
                ""));
    }

    @Test
    public void forCustomType() {
        Parameters parameters = new Parameters();
        parameters.add("field any_type");
        assertThat(new BuildSqlStatementParameterSettings().please(parameters), equalTo("" +
                "        statement.setObject(1, field);\n" +
                ""));
    }

    @Test
    public void camelCasesFieldName() {
        Parameters parameters = new Parameters();
        parameters.add("any_field any_type");
        assertThat(new BuildSqlStatementParameterSettings().please(parameters), equalTo("" +
                "        statement.setObject(1, anyField);\n" +
                ""));
    }

    @Test
    public void severalEntriesAreOnSeparateLines() {
        Parameters parameters = new Parameters();
        parameters.add("field1 varchar2");
        parameters.add("field2 varchar2");
        parameters.add("field3 number");
        parameters.add("field4 date");
        parameters.add("field5 any_type");
        assertThat(new BuildSqlStatementParameterSettings().please(parameters), equalTo("" +
                "        statement.setString(1, field1);\n" +
                "        statement.setString(2, field2);\n" +
                "        statement.setBigDecimal(3, field3);\n" +
                "        statement.setTimestamp(4, new java.sql.Timestamp(field4.getTime()));\n" +
                "        statement.setObject(5, field5);\n" +
                ""));
    }

    @Test
    public void parametersSettingsCanBeEmpty() {
        Parameters parameters = new Parameters();
        assertThat(new BuildSqlStatementParameterSettings().please(parameters), equalTo(""));
    }
}
