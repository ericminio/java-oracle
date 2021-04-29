package ericminio.javaoracle.domain;

public class BuildEqualsReturnValue extends BuildSomethingWithParameters {

    @Override
    protected String modify(String output, int index, String name, String type, boolean isLast) {
        return output
                + "                "
                + (index > 0 ? "&& ": "")
                + "this.get" + pascalCase.please(name) + "().equals(other.get" + pascalCase.please(name) + "())"
                + (!isLast ? "\n" : "")
                ;
    }
}
