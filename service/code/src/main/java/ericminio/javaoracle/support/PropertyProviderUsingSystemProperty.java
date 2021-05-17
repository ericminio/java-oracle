package ericminio.javaoracle.support;

public class PropertyProviderUsingSystemProperty implements PropertyProvider {

    @Override
    public String getValue(String key) {
        return System.getProperty(key);
    }
}
