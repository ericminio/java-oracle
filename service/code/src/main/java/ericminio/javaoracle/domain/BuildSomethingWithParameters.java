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
            String specification = parameters.get(index).toLowerCase();
            specification = specification.replaceAll("\\s+", " ");
            if (specification.indexOf(" in ") != -1) {
                specification = specification.replace(" in ", " ");
            }
            String[] parts = specification.trim().split("\\s");
            String name = parts[0];
            String type = parts[1];
            output = modify(output, index, name, type, index == parameters.count()-1);
        }
        return output;
    }

    protected abstract String modify(String output, int index, String name, String type, boolean isLast);
}
