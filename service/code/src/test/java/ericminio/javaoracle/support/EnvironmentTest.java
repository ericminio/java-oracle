package ericminio.javaoracle.support;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class EnvironmentTest {

    @Before
    public void reset() {
        System.setProperty("oracle.url", "");
        System.setProperty("oracle.username", "");
        System.setProperty("oracle.password", "");
    }

    @Test
    public void defaultOracleUrl() {
        assertThat(new Environment().getOracleUrl(),
                equalTo("dbc:oracle:thin:@localhost:1521:XE"));
    }
    @Test
    public void oracleUrl() {
        System.setProperty("oracle.url", "use-this-url");
        assertThat(new Environment().getOracleUrl(),
                equalTo("use-this-url"));
    }

    @Test
    public void defaultUsername() {
        assertThat(new Environment().getOracleUsername(),
                equalTo("system"));
    }
    @Test
    public void oracleUsername() {
        System.setProperty("oracle.username", "use-this-username");
        assertThat(new Environment().getOracleUsername(),
                equalTo("use-this-username"));
    }

    @Test
    public void defaultPassword() {
        assertThat(new Environment().getOraclePassword(),
                equalTo("oracle"));
    }
    @Test
    public void oraclePassword() {
        System.setProperty("oracle.password", "use-this-password");
        assertThat(new Environment().getOraclePassword(),
                equalTo("use-this-password"));
    }
}
