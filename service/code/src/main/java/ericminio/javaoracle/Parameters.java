package ericminio.javaoracle;

import java.util.ArrayList;
import java.util.List;

public class Parameters {

    List<String> parameterSpecifications;

    public Parameters() {
        parameterSpecifications = new ArrayList<>();
    }

    public int size() {
        return parameterSpecifications.size();
    }

    public String toList() {
        String list = "";
        for (int i=0; i<parameterSpecifications.size(); i++) {
            String parameterSpecification = parameterSpecifications.get(i);
            String[] parts = parameterSpecification.trim().split("\\s|,");
            String name = parts[0];
            String type = parts[1];
            list += (new TypeMapper().javaTypeOf(type) + " " + name);
            if (i != parameterSpecifications.size()-1) {
                list += ", ";
            }
        }
        return list;
    }

    public void add(String parameterSpecification) {
        parameterSpecifications.add(parameterSpecification);
    }

    public String getParametersSettings() {
        String settings = "";
        for (int i=0; i<parameterSpecifications.size(); i++) {
            String parameterSpecification = parameterSpecifications.get(i);
            String[] parts = parameterSpecification.trim().split("\\s|,");
            String name = parts[0];
            String type = parts[1];
            settings += ("        statement." + new TypeMapper().setterOf(type) + "(" + (i+2) + ", " + name + ");\n");
        }
        return settings;
    }
}
