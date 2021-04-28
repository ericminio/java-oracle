package ericminio.javaoracle.domain;

import ericminio.javaoracle.support.CamelCase;

public class BuildFieldDeclarationStatements extends BuildSomethingWithParameters {

    @Override
    protected String modify(String output, int index, String name, String type, boolean isLast) {
        return output
                + "    private " + new TypeMapperFactory().of(type).javaType()+ " " + new CamelCase().please(name) + ";"
                + (!isLast ? "\n" : "")
                ;
    }
}
