package ericminio.javaoracle.domain;

public class BuildReadSql extends BuildSomethingWithParameters {

    public BuildReadSql(TypeMapperFactory typeMapperFactory) {
        super(typeMapperFactory);
    }

    @Override
    protected String modify(String output, int index, Parameter parameter, boolean isLast) {
        String name = parameter.getName();
        String type = parameter.getType();
        return output
                + "        this.set" + pascalCase.please(name) + "(" + this.typeMapperFactory.of(type).sqlInputRead() + ");"
                + (!isLast ? "\n" : "")
                ;
    }
}
