package ericminio.javaoracle.domain;

public class BuildDeclarationStatements extends BuildSomethingWithParameters {

    @Override
    protected String modify(String output, int index, String name, String type, boolean isLast) {
        return output
                + "    private " + typeMapperFactory.of(type).javaType()+ " " + camelCase.please(name) + ";"
                + (!isLast ? "\n" : "")
                ;
    }
}
