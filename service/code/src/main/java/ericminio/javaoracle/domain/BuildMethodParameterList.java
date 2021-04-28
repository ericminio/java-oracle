package ericminio.javaoracle.domain;

public class BuildMethodParameterList {

    public String please(Parameters parameters) {
        String list = "";
        for (int i = 0; i< parameters.count(); i++) {
            String specification = parameters.get(i);
            String[] parts = specification.trim().split("\\s");
            String name = parts[0];
            String type = parts[1];
            list += (new TypeMapperFactory().of(type).javaType() + " " + name);
            if (i != parameters.count()-1) {
                list += ", ";
            }
        }
        return list;
    }
}
