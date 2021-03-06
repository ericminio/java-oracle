package ericminio.javaoracle.domain;

public class BuildEqualsReturnValue extends BuildSomethingWithParameters {

    public BuildEqualsReturnValue(TypeMapperFactory typeMapperFactory) {
        super(typeMapperFactory);
    }

    @Override
    protected String modify(String output, int index, Parameter parameter, boolean isLast) {
        String name = parameter.getName();
        return output
                + "                "
                + (index > 0 ? "&& ": "")
                + "(this.get" + pascalCase.please(name) + "() == null ? other.get" + pascalCase.please(name) + "() == null : "
                + "this.get" + pascalCase.please(name) + "().equals(other.get" + pascalCase.please(name) + "()))"
                + (!isLast ? "\n" : "")
                ;
    }
}
