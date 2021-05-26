package ericminio.javaoracle.domain;

public class BuildSqlStatementParameterSettings extends BuildSomethingWithParameters {

    public BuildSqlStatementParameterSettings(TypeMapperFactory typeMapperFactory) {
        super(typeMapperFactory);
    }

    @Override
    protected String modify(String output, int index, Parameter parameter, boolean isLast) {
        String type = parameter.getType();
        String statement = typeMapperFactory.of(type).functionParameterSettingStatement();
        if (parameter.isOut()) {
            statement = "statement.registerOutParameter(index, " + typeMapperFactory.of(type).functionParameterOutType() + ");";
        }
        statement = statement
                .replace("index", ""+(1+index+1))
                .replace("field", camelCase.please(parameter.getName()));
        return output
                + "        "
                + statement
                + "\n";
    }
}
