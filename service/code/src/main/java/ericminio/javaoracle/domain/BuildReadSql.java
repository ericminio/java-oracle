package ericminio.javaoracle.domain;

public class BuildReadSql extends BuildSomethingWithParameters {

    public BuildReadSql(TypeMapperFactory typeMapperFactory) {
        super(typeMapperFactory);
    }

    @Override
    protected String modify(String output, int index, String name, String type, boolean isLast) {
        return output
                + "        this.set" + pascalCase.please(name) + "(" + this.typeMapperFactory.of(type).sqlInputRead() + ");"
                + (!isLast ? "\n" : "")
                ;
    }
}
