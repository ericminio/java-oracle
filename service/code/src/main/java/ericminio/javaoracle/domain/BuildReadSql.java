package ericminio.javaoracle.domain;

public class BuildReadSql extends BuildSomethingWithParameters {

    @Override
    protected String modify(String output, int index, String name, String type, boolean isLast) {
        return output
                + "        this.set" + pascalCase.please(name) + "(stream." + typeMapperFactory.of(type).sqlInputRead() + "());"
                + (!isLast ? "\n" : "")
                ;
    }
}
