package ericminio.javaoracle.domain;

public class BuildSqlStatementParameterSettings extends BuildSomethingWithParameters {

    @Override
    protected String modify(String output, int index, String name, String type, boolean isLast) {
        return output
                + "        statement."
                + typeMapperFactory.of(type).sqlStatementSetter() + "(" + (index + 1) + ", " + name + ");\n";
    }
}
