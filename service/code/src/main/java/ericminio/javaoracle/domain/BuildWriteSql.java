package ericminio.javaoracle.domain;

public class BuildWriteSql extends BuildSomethingWithParameters {

    public BuildWriteSql(TypeMapperFactory typeMapperFactory) {
        super(typeMapperFactory);
    }

    @Override
    protected String modify(String output, int index, Parameter parameter, boolean isLast) {
        String name = parameter.getName();
        String type = parameter.getType();
        if (this.typeMapperFactory.isArrayType(type)) {
            return output
                    + "        // ignore " + camelCase.please(name)
                    + (!isLast ? "\n" : "")
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
