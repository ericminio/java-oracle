package ericminio.javaoracle.domain;

public class BuildDeclarationStatements extends BuildSomethingWithParameters {

    public BuildDeclarationStatements(TypeMapperFactory typeMapperFactory) {
        super(typeMapperFactory);
    }

    @Override
    protected String modify(String output, int index, Parameter parameter, boolean isLast) {
        String name = parameter.getName();
        String type = parameter.getType();
        return output
                + "    private " + typeMapperFactory.of(type).javaType()+ " " + camelCase.please(name) + ";"
                + (!isLast ? "\n" : "")
                ;
    }
}
