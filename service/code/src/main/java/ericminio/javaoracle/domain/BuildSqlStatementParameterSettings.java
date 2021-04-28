package ericminio.javaoracle.domain;

public class BuildSqlStatementParameterSettings {

    public String please(Parameters parameters) {
        String settings = "";
        for (int i = 0; i< parameters.count(); i++) {
            String specification = parameters.get(i);
            String[] parts = specification.trim().split("\\s");
            String name = parts[0];
            String type = parts[1];
            settings += ("        statement." + new TypeMapperFactory().of(type).setter() + "(" + (i+1) + ", " + name + ");\n");
        }
        return settings;
    }
}
