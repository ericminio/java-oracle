package ericminio.javaoracle;

import ericminio.javaoracle.support.PascalCase;
import ericminio.javaoracle.domain.Generator;
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

    private static void debug(String message) {
        System.out.println(message);
    }

    public void go(String oraclePackage, String javaPackage, String outputFolder) throws Exception {
        List<String> packageSpecification = with(new Database().connection())
                .selectPackageDefinition(oraclePackage);
        Generator generator = new Generator();
        String code = generator.generate(packageSpecification);;
        code = "package " + javaPackage + ";\n" + code;
        Path path = Paths.get(outputFolder, new PascalCase().please(generator.getPackageName())+".java");
        Files.write(path, code.getBytes());
    }
}
