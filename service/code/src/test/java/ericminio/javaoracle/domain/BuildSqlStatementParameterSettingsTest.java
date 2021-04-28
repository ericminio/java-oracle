package ericminio.javaoracle.domain;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class BuildSqlStatementParameterSettingsTest {

    @Test
    public void parametersSettings() {
        Parameters parameters = new Parameters();
        parameters.add("field1 INTEGER");
        parameters.add("field2 integer");
        parameters.add("field3 varchar2");
        parameters.add("field4 VARCHAR2(5)");
        assertThat(new BuildSqlStatementParameterSettings().please(parameters), equalTo("" +
                "        statement.setInt(1, field1);\n" +
                "        statement.setInt(2, field2);\n" +
                "        statement.setString(3, field3);\n" +
                "        statement.setString(4, field4);\n" +
                ""));
    }

    @Test
    public void parametersSettingsCanBeEmpty() {
        Parameters parameters = new Parameters();
        assertThat(new BuildSqlStatementParameterSettings().please(parameters), equalTo(""));
    }
}
