package ericminio.javaoracle;

import ericminio.javaoracle.data.Database;
import ericminio.javaoracle.domain.GeneratePackageCode;
import ericminio.javaoracle.support.PascalCase;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class GeneratePackageAdapter {

    public static void main(String[] args) {
        GeneratePackageAdapter generatePackageAdapter = new GeneratePackageAdapter();
        try {
            generatePackageAdapter.go(
                    System.getProperty("oraclePackage"),
                    System.getProperty("javaPackage"),
                    System.getProperty("outputFolder")
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void go(String oraclePackage, String javaPackage, String outputFolder) throws Exception {
        List<String> packageSpecification = new Database().selectPackageDefinition(oraclePackage);
        GeneratePackageCode generatePackageCode = new GeneratePackageCode();
        String code = generatePackageCode.please(packageSpecification);;
        code = "package " + javaPackage + ";\n\n" + code;
        Path path = Paths.get(outputFolder, new PascalCase().please(generatePackageCode.getPackageName())+".java");
        Files.write(path, code.getBytes());
    }
}
