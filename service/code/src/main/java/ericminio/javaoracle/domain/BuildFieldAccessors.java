package ericminio.javaoracle.domain;

public class BuildFieldAccessors extends BuildSomethingWithParameters {

    @Override
    protected String modify(String output, int index, String name, String type, boolean isLast) {
        return output
                + "    public " + typeMapperFactory.of(type).javaType() + " get" + pascalCase.please(name) + "() {\n"
                + "        return this." + camelCase.please(name) + ";\n"
                + "    }\n"
                + "    public void set" + pascalCase.please(name) + "(" + typeMapperFactory.of(type).javaType() + " " + camelCase.please(name) + ") {\n"
                + "        this." + camelCase.please(name) + " = " + camelCase.please(name) + ";\n"
                + "    }\n"
                + (!isLast ? "\n": "")
                ;
    }
}
