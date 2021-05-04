package ericminio.javaoracle.domain;

public class BuildWriteSql extends BuildSomethingWithParameters {

    private TypeMapperFactory typeMapperFactory;

    public BuildWriteSql(TypeMapperFactory typeMapperFactory) {
        this.typeMapperFactory = typeMapperFactory;
    }

    @Override
    protected String modify(String output, int index, String name, String type, boolean isLast) {
        if (this.typeMapperFactory.isArrayType(type)) {
            return output
                    + "        // ignore " + camelCase.please(name)
                    ;
        }

        String statement = typeMapperFactory.of(type).sqlOutputWrite()
                .replace("Field", pascalCase.please(name))
                ;
        return output
                + "        "
                + statement
                + (!isLast ? "\n" : "")
                ;
    }
}
