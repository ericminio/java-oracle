package ericminio.javaoracle.domain;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class BuildOutParameterSettingsTest {

    BuildOutParameterSettings buildOutParameterSettings;

    @Before
    public void sut() {
        List<List<String>> typeSpecifications = Arrays.asList(
                Arrays.asList("create type any_type as object(value number)")
        );
        TypeMapperFactory typeMapperFactory = new TypeMapperFactory(typeSpecifications);
        buildOutParameterSettings = new BuildOutParameterSettings(typeMapperFactory);
    }

    @Test
    public void forString() {
        Parameters parameters = new Parameters();
        parameters.add("field out varchar2");
        assertThat(buildOutParameterSettings.please(parameters), equalTo("" +
                "        Object outField = statement.getObject(2);\n" +
                "        field[0] = (String) outField;\n" +
                ""));
    }

    @Test
    public void emptyForInParameter() {
        Parameters parameters = new Parameters();
        parameters.add("field varchar2");
        assertThat(buildOutParameterSettings.please(parameters), equalTo(""));
    }

    @Test
    public void emptyForInParameterWithInKeyword() {
        Parameters parameters = new Parameters();
        parameters.add("field in varchar2");
        assertThat(buildOutParameterSettings.please(parameters), equalTo(""));
    }

    @Test
    public void forNumber() {
        Parameters parameters = new Parameters();
        parameters.add("field out number");
        assertThat(buildOutParameterSettings.please(parameters), equalTo("" +
                "        Object outField = statement.getObject(2);\n" +
                "        field[0] = (BigDecimal) outField;\n" +
                ""));
    }

    @Test
    public void forDate() {
        Parameters parameters = new Parameters();
        parameters.add("field out date");
        assertThat(buildOutParameterSettings.please(parameters), equalTo("" +
                "        Object outField = statement.getObject(2);\n" +
                "        field[0] = outField == null ? null : new Date( ((java.sql.Timestamp) outField).getTime() );\n" +
                ""));
    }
}
