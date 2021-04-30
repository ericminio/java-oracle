package ericminio.javaoracle.domain;

public class BuildWriteSql extends BuildSomethingWithParameters {

    @Override
    protected String modify(String output, int index, String name, String type, boolean isLast) {
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
