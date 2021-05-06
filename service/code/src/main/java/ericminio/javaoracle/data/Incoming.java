package ericminio.javaoracle.data;

import java.util.List;

public class Incoming {

    private List<String> packageSpecification;
    private List<List<String>> typeSpecifications;
    private List<String> typeNames;

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
    }

    public List<String> getTypeNames() {
        return typeNames;
    }

    public void setTypeNames(List<String> typeNames) {
        this.typeNames = typeNames;
    }
}
