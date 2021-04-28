package ericminio.javaoracle.domain;

public class BuildFieldDeclarationStatements extends BuildSomethingWithParameters {

    @Override
    protected String modify(String output, int index, String name, String type, boolean isLast) {
        return output
                + "        " + "private "
                + new TypeMapperFactory().of(type).javaType()
                + " " + name + ";"
                + (!isLast ? "\n" : "")
                ;
    }
}
