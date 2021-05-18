package ericminio.javaoracle.domain;

import java.util.ArrayList;
import java.util.List;

public class Incoming {

    private List<String> packageSpecification;
    private List<List<String>> typeSpecifications;
    private List<String> typeNames;

    public Incoming() {
        this.packageSpecification = new ArrayList<String>();
        this.typeSpecifications = new ArrayList<List<String>>();
        this.typeNames = new ArrayList<String>();
    }

    public void setPackageSpecification(List<String> packageSpecification) {
        this.packageSpecification = packageSpecification;
        this.typeSpecifications = extendsWithTypeSpecificationsFromPackageSpecification(this.typeSpecifications);
        extractTypeNames();
    }

    public void setTypeSpecifications(List<List<String>> typeSpecifications) {
        this.typeSpecifications = extendsWithTypeSpecificationsFromPackageSpecification(typeSpecifications);
        extractTypeNames();
    }

    public List<String> getPackageSpecification() {
        return packageSpecification;
    }

    public List<List<String>> getTypeSpecifications() {
        return typeSpecifications;
    }

    public List<String> getTypeNames() {
        return typeNames;
    }

    private List<List<String>> extendsWithTypeSpecificationsFromPackageSpecification(List<List<String>> typeSpecifications) {
        List<List<String>> additionalTypeSpecifications = extractTypeSpecificationsFromPackageSpecification();
        List<List<String>> extended = new ArrayList<>();
        for (int i = 0; i < typeSpecifications.size(); i++) {
            extended.add(typeSpecifications.get(i));
        }
        for (int i = 0; i < additionalTypeSpecifications.size(); i++) {
            extended.add(additionalTypeSpecifications.get(i));
        }
        return extended;
    }

    private List<List<String>> extractTypeSpecificationsFromPackageSpecification() {
        List<List<String>> typeSpecifications = new ArrayList<>();
        List<String> typeSpecification = new ArrayList<>();
        for (int i = 0; i< packageSpecification.size(); i++) {
            String line = packageSpecification.get(i).trim();
            if (line.toUpperCase().startsWith("TYPE") || typeSpecification.size() > 0) {
                typeSpecification.add(line);
                if (line.endsWith(";")) {
                    typeSpecifications.add(typeSpecification);
                    typeSpecification = new ArrayList<>();
                }
            }
        }
        return typeSpecifications;
    }

    private void extractTypeNames() {
        List<String> names = new ArrayList<>();
        for (int i = 0; i< typeSpecifications.size(); i++) {
            String name = new ExtractTypeName().please(typeSpecifications.get(i));
            names.add(name);
        }
        setTypeNames(names);
    }

    private void setTypeNames(List<String> typeNames) {
        this.typeNames = typeNames;
    }
}
