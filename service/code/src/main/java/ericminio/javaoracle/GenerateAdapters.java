package ericminio.javaoracle;

import ericminio.javaoracle.data.Database;
import ericminio.javaoracle.domain.GenerateArrayTypeCode;
import ericminio.javaoracle.domain.GenerateClassCode;
import ericminio.javaoracle.domain.GenerateTypeCode;
import ericminio.javaoracle.domain.TypeMapperFactory;
import ericminio.javaoracle.support.LogSink;
import ericminio.javaoracle.support.PascalCase;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GenerateAdapters {

    private final LogSink logSink;
    private final Logger logger;

    public GenerateAdapters() {
        this.logSink = new LogSink(true);
        this.logger = logSink.getLogger();
    }

    public static void main(String[] args) {
        GenerateAdapters generateAdapters = new GenerateAdapters();
        try {
            generateAdapters.go(
                    System.getProperty("oraclePackage"),
                    System.getProperty("typeNamePrefix"),
                    System.getProperty("javaPackage"),
                    System.getProperty("outputFolder")
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void go(String oraclePackage, String typeNamePrefix, String javaPackage, String outputFolder) throws SQLException, IOException {
        List<String> packageSpecification = new Database().selectPackageDefinition(oraclePackage);
        List<String> types = new Database().selectDistinctTypeNamesWithPrefix(typeNamePrefix);
        List<List<String>> typeSpecifications = new ArrayList<>();
        for (String type:types) {
            List<String> typeSpecification = new Database().selectTypeDefinition(type);
            typeSpecifications.add(typeSpecification);
        }
        TypeMapperFactory typeMapperFactory = new TypeMapperFactory(typeSpecifications);

        logger.log(Level.INFO, "Generating class for package");
        GenerateClassCode generateClassCode = new GenerateClassCode();
        String packageCode = generateClassCode.please(packageSpecification, typeMapperFactory);
        packageCode = "package " + javaPackage + ";\n\n" + packageCode;
        String packageClassName = new PascalCase().please(generateClassCode.getPackageName());
        Files.write(Paths.get(outputFolder, packageClassName +".java"), packageCode.getBytes());
        logger.log(Level.INFO, "-> " + javaPackage + "." + packageClassName);

        logger.log(Level.INFO, "Generating classes for types");
        for (int i=0; i<types.size(); i++) {
            String typeName = types.get(i);
            List<String> typeSpecification = typeSpecifications.get(i);
            String typeCode = "";
            if (typeMapperFactory.isArrayType(typeName)) {
                GenerateArrayTypeCode generateArrayTypeCode = new GenerateArrayTypeCode();
                typeCode = generateArrayTypeCode.please(typeSpecification);
            }
            else {
                GenerateTypeCode generateTypeCode = new GenerateTypeCode();
                typeCode = generateTypeCode.please(typeSpecification, typeMapperFactory);
            }
            typeCode = "package " + javaPackage + ";\n\n" + typeCode;
            String typeClassName = new PascalCase().please(typeName);
            Files.write(Paths.get(outputFolder, typeClassName +".java"), typeCode.getBytes());
            logger.log(Level.INFO, "-> " + javaPackage + "." + typeClassName);
        }
    }

    public String getLog() {
        return logSink.getLog();
    }

    public LogSink getLogSink() {
        return logSink;
    }
}
