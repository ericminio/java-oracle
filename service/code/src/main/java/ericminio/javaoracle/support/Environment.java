package ericminio.javaoracle.support;

public class Environment {

    public String getOracleUrl() {
        return getPropertyOr("oracle.url", "jdbc:oracle:thin:@localhost:1521:XE");
    }

    public String getOracleUsername() {
        String value = getPropertyOr("oracle.username", "system");
        return value;
    }

    public String getOraclePassword() {
        String value = getPropertyOr("oracle.password", "oracle");
        return value;
    }

    private String getPropertyOr(String key, String defaultValue) {
        String value = System.getProperty(key);
        if (value == null || value.trim().length() == 0) {
            value = defaultValue;
        }
        System.out.println(key + "=" + value);
        return value;
    }
}
