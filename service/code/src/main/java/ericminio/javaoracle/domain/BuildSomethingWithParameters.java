package ericminio.javaoracle.domain;

import ericminio.javaoracle.support.CamelCase;
import ericminio.javaoracle.support.PascalCase;

public abstract class BuildSomethingWithParameters {

    protected final PascalCase pascalCase;
    protected final CamelCase camelCase;
    protected final TypeMapperFactory typeMapperFactory;

    public BuildSomethingWithParameters(TypeMapperFactory typeMapperFactory) {
        pascalCase = new PascalCase();
        camelCase = new CamelCase();
        this.typeMapperFactory = typeMapperFactory;
    }

    public String please(Parameters parameters) {
        String output = "";
        for (int index = 0; index< parameters.count(); index++) {
            Parameter parameter = parameters.getParameter(index);
            output = modify(output, index, parameter, index == parameters.count()-1);
        }
        return output;
    }

    protected abstract String modify(String output, int index, Parameter parameter, boolean isLast);
}
