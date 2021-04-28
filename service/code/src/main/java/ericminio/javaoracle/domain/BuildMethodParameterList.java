package ericminio.javaoracle.domain;

public class BuildMethodParameterList extends BuildSomethingWithParameters {

    @Override
    protected String modify(String output, int index, String name, String type, boolean isLast) {
        return output
                + new TypeMapperFactory().of(type).javaType() + " " + name
                + (!isLast ? ", ": "")
                ;
    }
}
