package ericminio.javaoracle;

import ericminio.javaoracle.data.Database;
import ericminio.javaoracle.domain.GenerateTypeCode;
import ericminio.javaoracle.support.PascalCase;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class GenerateTypeAdapter {

    public static void main(String[] args) {
        GenerateTypeAdapter generator = new GenerateTypeAdapter();
        try {
            generator.go(
                    System.getProperty("typeName"),
                    System.getProperty("javaPackage"),
                    System.getProperty("outputFolder")
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void go(String typeName, String javaPackage, String outputFolder) throws Exception {
        List<String> typeSpecification = new Database().selectTypeDefinition(typeName);
        GenerateTypeCode generateTypeCode = new GenerateTypeCode();
        String code = generateTypeCode.please(typeSpecification);;
        code = "package " + javaPackage + ";\n" + code;
        Path path = Paths.get(outputFolder, new PascalCase().please(typeName)+".java");
        Files.write(path, code.getBytes());
    }
}
