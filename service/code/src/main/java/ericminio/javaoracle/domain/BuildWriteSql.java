package ericminio.javaoracle.domain;

public class BuildWriteSql extends BuildSomethingWithParameters {

    @Override
    protected String modify(String output, int index, String name, String type, boolean isLast) {
        return output
                + "        stream." + typeMapperFactory.of(type).sqlOutputWrite() + "(this.get" + pascalCase.please(name) + "());"
                + (!isLast ? "\n" : "")
                ;
    }
}
