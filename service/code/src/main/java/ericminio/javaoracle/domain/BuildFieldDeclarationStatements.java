package ericminio.javaoracle.domain;

public class BuildFieldDeclarationStatements {

    public String please(Parameters parameters) {
        String statements = "";
        for (int i=0; i< parameters.count(); i++) {
            String specification = parameters.get(i);
            String[] parts = specification.trim().split("\\s");
            String name = parts[0];
            String type = parts[1];
            String statement = "private " + new TypeMapperFactory().of(type).javaType() + " " + name + ";";
            statements += ("        " + statement);
            if (i != parameters.count()-1) {
                statements += "\n";
            }
        }
        return statements;
    }
}
