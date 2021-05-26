package ericminio.javaoracle.domain;

public class BuildHashcodeReturnValue extends BuildSomethingWithParameters {

    public BuildHashcodeReturnValue(TypeMapperFactory typeMapperFactory) {
        super(typeMapperFactory);
    }

    @Override
    protected String modify(String output, int index, Parameter parameter, boolean isLast) {
        String name = parameter.getName();
        return output
                + "                "
                + (index > 0 ? "+ ": "")
                + "(this.get" + pascalCase.please(name) + "() == null ? 0 : this.get" + pascalCase.please(name) + "().hashCode())"
                + (!isLast ? "\n" : "")
                ;
    }
}
