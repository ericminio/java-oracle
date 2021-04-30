package ericminio.javaoracle;

import ericminio.javaoracle.data.Database;

import java.util.List;

public class GenerateTypeAdapters {

    public static void main(String[] args) {
        GenerateTypeAdapters generator = new GenerateTypeAdapters();
        try {
            generator.go(
                    System.getProperty("typeNamePrefix"),
                    System.getProperty("javaPackage"),
                    System.getProperty("outputFolder")
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void go(String typeNamePrefix, String javaPackage, String outputFolder) throws Exception {
        List<String> typeNames = new Database().selectDistinctTypeNamesWithPrefix(typeNamePrefix);
        for (int i=0; i<typeNames.size(); i++) {
            new GenerateTypeAdapter().go(typeNames.get(i), javaPackage, outputFolder);
        }
    }
}
