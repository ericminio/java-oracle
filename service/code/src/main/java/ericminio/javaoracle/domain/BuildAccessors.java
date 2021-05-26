package ericminio.javaoracle.domain;

public class BuildAccessors extends BuildSomethingWithParameters {

    public BuildAccessors(TypeMapperFactory typeMapperFactory) {
        super(typeMapperFactory);
    }

    @Override
    protected String modify(String output, int index, Parameter parameter, boolean isLast) {
        String name = parameter.getName();
        String type = parameter.getType();
        return output
                + (index > 0 ? "\n" : "")
                + "    public " + typeMapperFactory.of(type).javaType() + " get" + pascalCase.please(name) + "() {\n"
                + "        return this." + camelCase.please(name) + ";\n"
                + "    }\n"
                + "    public void set" + pascalCase.please(name) + "(" + typeMapperFactory.of(type).javaType() + " " + camelCase.please(name) + ") {\n"
                + "        this." + camelCase.please(name) + " = " + camelCase.please(name) + ";\n"
                + "    }"
                + (!isLast ? "\n": "")
                ;
    }
}
