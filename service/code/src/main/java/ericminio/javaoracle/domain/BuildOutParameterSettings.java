package ericminio.javaoracle.domain;

public class BuildOutParameterSettings extends BuildSomethingWithParameters {

    public BuildOutParameterSettings(TypeMapperFactory typeMapperFactory) {
        super(typeMapperFactory);
    }

    @Override
    protected String modify(String output, int index, Parameter parameter, boolean isLast) {
        if (!parameter.isOut()) {
            return "";
        }
        String name = parameter.getName();
        String type = parameter.getType();
        String tmpName = "out" + pascalCase.please(name);
        String casting = typeMapperFactory.of(type).cast().replace("data", tmpName);
        String statement = "        Object " + tmpName + " = statement.getObject(index);\n";
        statement = statement.replace("index", ""+(1+index+1));
        statement += "        " + camelCase.please(name) + "[0] = " + casting;
        return output
                + statement
                + "\n";
    }
}
