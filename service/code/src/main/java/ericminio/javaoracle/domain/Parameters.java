package ericminio.javaoracle.domain;

import java.util.ArrayList;
import java.util.List;

public class Parameters {

    List<String> specifications;
    List<Parameter> parameters;

    public Parameters() {
        specifications = new ArrayList<>();
        parameters = new ArrayList<>();
    }

    public void add(String parameterSpecification) {
        specifications.add(parameterSpecification);
        parameters.add(new Parameter(parameterSpecification));
    }

    public int count() {
        return parameters.size();
    }

    public List<String> all() {
        return this.specifications;
    }

    public String get(int i) {
        return specifications.get(i);
    }

    public Parameter getParameter(int i) {
        return parameters.get(i);
    }

    public boolean hasDateField() {
        for (int i=0; i<count(); i++) {
            if (getParameter(i).getType().equalsIgnoreCase("date")) {
                return true;
            }
        }
        return false;
    }
}
