package ericminio.javaoracle.support;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static ericminio.javaoracle.support.Environment.EXPECTING_FORMAT_ORACLE_VALUE_USER_PASSWORD;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.fail;

public class EnvironmentTest {

    private Environment environment;
    private MockPropertyProvider propertyProvider;

    @Before
    public void sut() {
        environment = new Environment();
        propertyProvider = new MockPropertyProvider();
        environment.setPropertyProvider(propertyProvider);
    }

    private class MockPropertyProvider implements PropertyProvider {
        private Map<String,String> values;

        public MockPropertyProvider() {
            values = new HashMap<>();
        }

        public void setUrl(String value) {
            this.values.put("oracle.url", value);
        }

        @Override
        public String getValue(String key) {
            return values.get(key);
        }

        public void setUsername(String value) {
            this.values.put("oracle.username", value);
        }

        public void setPassword(String value) {
            this.values.put("oracle.password", value);
        }

        public void setUser(String value) {
            this.values.put("oracle.user", value);
        }
    }

    @Test
    public void getUrlFromProvider() {
        propertyProvider.setUrl("this-url");
        assertThat(environment.getOracleUrl(), equalTo("this-url"));
    }

    @Test
    public void getUrlFromProviderOrDefaultValue() {
        propertyProvider.setUrl(null);
        assertThat(environment.getOracleUrl(), equalTo("jdbc:oracle:thin:@localhost:1521:XE"));
    }

    @Test
    public void getUsernameFromProvider() {
        propertyProvider.setUsername("this-username");
        assertThat(environment.getOracleUsername(), equalTo("this-username"));
    }

    @Test
    public void getUsernameFromProviderOrDefaultValue() {
        propertyProvider.setUsername(null);
        assertThat(environment.getOracleUsername(), equalTo("system"));
    }

    @Test
    public void getPasswordFromProvider() {
        propertyProvider.setPassword("this-password");
        assertThat(environment.getOraclePassword(), equalTo("this-password"));
    }

    @Test
    public void getPasswordFromProviderOrDefaultValue() {
        propertyProvider.setPassword(null);
        assertThat(environment.getOraclePassword(), equalTo("oracle"));
    }

    @Test
    public void usernameAndPasswordDefinedTogetherTakeOver() {
        propertyProvider.setPassword("this-password");
        propertyProvider.setUsername("this-username");
        propertyProvider.setUser("user/password");
        assertThat(environment.getOracleUsername(), equalTo("user"));
        assertThat(environment.getOraclePassword(), equalTo("password"));
    }

    @Test
    public void notifiesWhenUserMalformedWhenGettingUsername() {
        propertyProvider.setUser("user");
        try {
            environment.getOracleUsername();
            fail();
        }
        catch (Exception failing) {
            assertThat(failing.getMessage(), equalTo(EXPECTING_FORMAT_ORACLE_VALUE_USER_PASSWORD));
        }
    }

    @Test
    public void notifiesWhenUserMalformedWhenGettingPassword() {
        propertyProvider.setUser("user");
        try {
            environment.getOraclePassword();
            fail();
        }
        catch (Exception failing) {
            assertThat(failing.getMessage(), equalTo(EXPECTING_FORMAT_ORACLE_VALUE_USER_PASSWORD));
        }
    }
}
