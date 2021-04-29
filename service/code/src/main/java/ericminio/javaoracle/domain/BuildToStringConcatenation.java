package ericminio.javaoracle.domain;

public class BuildToStringConcatenation extends BuildSomethingWithParameters {

    @Override
    protected String modify(String output, int index, String name, String type, boolean isLast) {
        return output
                + "                "
                + "+ \""
                + (index > 0 ? "," : "")
                + " " + camelCase.please(name) + "=\" + (this.get" + pascalCase.please(name) + "() == null ? \"null\" : this.get" + pascalCase.please(name) + "().toString())"
                + (!isLast ? "\n" : "")
                ;
    }
}
