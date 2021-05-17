package ericminio.javaoracle.support;

public class Environment {

    public static final String EXPECTING_FORMAT_ORACLE_VALUE_USER_PASSWORD = "expecting format oracle.value=<user>/<password>";
    private PropertyProvider propertyProvider;

    public Environment() {
        setPropertyProvider(new PropertyProviderUsingSystemProperty());
    }

    public void setPropertyProvider(PropertyProvider propertyProvider) {
        this.propertyProvider = propertyProvider;
    }

    public PropertyProvider getPropertyProvider() {
        return propertyProvider;
    }

    public String getOracleUrl() {
        return getPropertyOr("oracle.url", "jdbc:oracle:thin:@localhost:1521:XE");
    }

    public String getOracleUsername() {
        String value = getPropertyProvider().getValue("oracle.user");
        if (value != null) {
            if (value.indexOf("/") == -1) {
                throw new RuntimeException(EXPECTING_FORMAT_ORACLE_VALUE_USER_PASSWORD);
            }
            return value.substring(0, value.indexOf("/"));
        }
        value = getPropertyOr("oracle.username", "system");
        return value;
    }

    public String getOraclePassword() {
        String value = getPropertyProvider().getValue("oracle.user");
        if (value != null) {
            if (value.indexOf("/") == -1) {
                throw new RuntimeException(EXPECTING_FORMAT_ORACLE_VALUE_USER_PASSWORD);
            }
            return value.substring(value.indexOf("/")+1);
        }
        value = getPropertyOr("oracle.password", "oracle");
        return value;
    }

    private String getPropertyOr(String key, String defaultValue) {
        String value = getPropertyProvider().getValue(key);
        if (value == null || value.trim().length() == 0) {
            value = defaultValue;
        }
        return value;
    }
}
