package ericminio.javaoracle.domain;

import java.util.ArrayList;
import java.util.List;

public class Parameters {

    List<String> specifications;

    public Parameters() {
        specifications = new ArrayList<>();
    }

    public void add(String parameterSpecification) {
        specifications.add(parameterSpecification);
    }

    public int count() {
        return specifications.size();
    }

    public List<String> all() {
        return this.specifications;
    }

    public String get(int i) {
        return specifications.get(i);
    }
}
