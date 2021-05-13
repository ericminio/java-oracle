package ericminio.javaoracle.domain;

public class BuildSqlStatementParameterSettings extends BuildSomethingWithParameters {

    public BuildSqlStatementParameterSettings(TypeMapperFactory typeMapperFactory) {
        super(typeMapperFactory);
    }

    @Override
    protected String modify(String output, int index, String name, String type, boolean isLast) {
        String statement = typeMapperFactory.of(type).functionParameterSettingStatement()
                .replace("index", ""+(index+1))
                .replace("field", camelCase.please(name))
                ;
        return output
                + "        "
                + statement
                + "\n";
    }
}
