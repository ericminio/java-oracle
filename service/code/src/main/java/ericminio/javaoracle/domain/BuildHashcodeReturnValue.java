package ericminio.javaoracle.domain;

import ericminio.javaoracle.support.PascalCase;

public class BuildHashcodeReturnValue extends BuildSomethingWithParameters {

    @Override
    protected String modify(String output, int index, String name, String type, boolean isLast) {
        return output
                + "                "
                + (index > 0 ? "+ ": "")
                + "this.get" + new PascalCase().please(name) + "().hashCode()"
                + (!isLast ? "\n" : "")
                ;
    }
}
