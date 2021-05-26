package ericminio.javaoracle.domain;

public class BuildMethodParameterList extends BuildSomethingWithParameters {

    public BuildMethodParameterList(TypeMapperFactory typeMapperFactory) {
        super(typeMapperFactory);
    }

    @Override
    protected String modify(String output, int index, Parameter parameter, boolean isLast) {
        return output
                + typeMapperFactory.of(parameter.getType()).javaType()
                + ( parameter.isOut() ? "[]" : "")
                + " " + camelCase.please(parameter.getName())
                + (!isLast ? ", ": "")
                ;
    }
}
