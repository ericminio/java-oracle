package ericminio.javaoracle.domain;

public class BuildHashcodeReturnValue extends BuildSomethingWithParameters {

    @Override
    protected String modify(String output, int index, String name, String type, boolean isLast) {
        return output
                + "                "
                + (index > 0 ? "+ ": "")
                + "this.get" + pascalCase.please(name) + "().hashCode()"
                + (!isLast ? "\n" : "")
                ;
    }
}
