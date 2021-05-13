package ericminio.javaoracle.domain;

public class BuildMethodParameterList extends BuildSomethingWithParameters {

    public BuildMethodParameterList(TypeMapperFactory typeMapperFactory) {
        super(typeMapperFactory);
    }

    @Override
    protected String modify(String output, int index, String name, String type, boolean isLast) {
        return output
                + typeMapperFactory.of(type).javaType() + " " + camelCase.please(name)
                + (!isLast ? ", ": "")
                ;
    }
}
