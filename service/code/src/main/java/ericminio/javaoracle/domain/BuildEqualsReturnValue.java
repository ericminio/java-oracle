package ericminio.javaoracle.domain;

import ericminio.javaoracle.support.PascalCase;

public class BuildEqualsReturnValue extends BuildSomethingWithParameters {

    @Override
    protected String modify(String output, int index, String name, String type, boolean isLast) {
        return output
                + "                "
                + (index > 0 ? "&& ": "")
                + "this.get" + new PascalCase().please(name) + "().equals(other.get" + new PascalCase().please(name) + "())"
                + (!isLast ? "\n" : "")
                ;
    }
}
