package ericminio.javaoracle.support;

public class Environment {

    public String getOracleHost() {
        String value = System.getProperty("oracle.host");
        if (value == null) {
            value = "localhost";
        }
        return value;
    }
}
