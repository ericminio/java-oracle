package ericminio.javaoracle.domain;

import ericminio.javaoracle.support.CamelCase;
import ericminio.javaoracle.support.PascalCase;

public class BuildFieldAccessors extends BuildSomethingWithParameters {

    @Override
    protected String modify(String output, int index, String name, String type, boolean isLast) {
        return output
                + "    public " + new TypeMapperFactory().of(type).javaType() + " get" + new PascalCase().please(name) + "() {\n"
                + "        return this." + new CamelCase().please(name) + ";\n"
                + "    }\n"
                + "    public void set" + new PascalCase().please(name) + "(" + new TypeMapperFactory().of(type).javaType() + " " + new CamelCase().please(name) + ") {\n"
                + "        this." + new CamelCase().please(name) + " = " + new CamelCase().please(name) + ";\n"
                + "    }\n"
                + (!isLast ? "\n": "")
                ;
    }
}
