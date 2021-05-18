package ericminio.javaoracle.domain;

import java.util.ArrayList;
import java.util.List;

public class Incoming {

    private List<String> packageSpecification;
    private List<List<String>> typeSpecifications;
    private List<String> typeNames;

    public Incoming() {
        setTypeNames(new ArrayList<String>());
        setPackageSpecification(new ArrayList<String>());
        setTypeSpecifications(new ArrayList<List<String>>());
    }

    public List<String> getPackageSpecification() {
        return packageSpecification;
    }

    public void setPackageSpecification(List<String> packageSpecification) {
        this.packageSpecification = packageSpecification;
    }

    public List<List<String>> getTypeSpecifications() {
        return typeSpecifications;
    }

    public void setTypeSpecifications(List<List<String>> typeSpecifications) {
        this.typeSpecifications = typeSpecifications;
        List<String> names = new ArrayList<>();
        for (int i=0; i<typeSpecifications.size(); i++) {
            String name = new ExtractTypeName().please(typeSpecifications.get(i));
            names.add(name);
        }
        setTypeNames(names);
    }

    public List<String> getTypeNames() {
        return typeNames;
    }

    private void setTypeNames(List<String> typeNames) {
        this.typeNames = typeNames;
    }
}
