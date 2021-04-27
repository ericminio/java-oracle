package ericminio.javaoracle;

import ericminio.javaoracle.support.PascalCase;
import ericminio.javaoracle.domain.GenerateClassCode;
import ericminio.javaoracle.support.Database;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static ericminio.javaoracle.support.Query.with;

public class ExtractPackage {

    public static void main(String[] args) {
        ExtractPackage extractPackage = new ExtractPackage();
        try {
            extractPackage.go(
                    System.getProperty("oraclePackage"),
                    System.getProperty("javaPackage"),
                    System.getProperty("outputFolder")
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void go(String oraclePackage, String javaPackage, String outputFolder) throws Exception {
        List<String> packageSpecification = with(new Database().connection())
                .selectPackageDefinition(oraclePackage);
        GenerateClassCode generateClassCode = new GenerateClassCode();
        String code = generateClassCode.please(packageSpecification);;
        code = "package " + javaPackage + ";\n" + code;
        Path path = Paths.get(outputFolder, new PascalCase().please(generateClassCode.getPackageName())+".java");
        Files.write(path, code.getBytes());
    }
}
