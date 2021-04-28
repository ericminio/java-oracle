package ericminio.javaoracle.domain;

public class BuildSqlStatementParameterSettings extends BuildSomethingWithParameters {

    @Override
    protected String modify(String output, int index, String name, String type, boolean isLast) {
        return output
                + "        statement."
                + new TypeMapperFactory().of(type).setter() + "(" + (index + 1) + ", " + name + ");\n";
    }
}
