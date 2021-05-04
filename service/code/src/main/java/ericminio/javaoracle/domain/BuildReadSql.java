package ericminio.javaoracle.domain;

public class BuildReadSql extends BuildSomethingWithParameters {

    private TypeMapperFactory typeMapperFactory;

    public BuildReadSql(TypeMapperFactory typeMapperFactory) {
        this.typeMapperFactory = typeMapperFactory;
    }

    @Override
    protected String modify(String output, int index, String name, String type, boolean isLast) {
        return output
                + "        this.set" + pascalCase.please(name) + "(" + this.typeMapperFactory.of(type).sqlInputRead() + ");"
                + (!isLast ? "\n" : "")
                ;
    }
}
