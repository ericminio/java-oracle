package ericminio.javaoracle;

import ericminio.javaoracle.data.Incoming;
import ericminio.javaoracle.data.GetDataFromDatabase;
import ericminio.javaoracle.domain.*;
import ericminio.javaoracle.support.LogSink;
import ericminio.javaoracle.support.PascalCase;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static ericminio.javaoracle.support.FileUtils.save;

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
            generateAdapters.fromDatabase(
                    System.getProperty("oraclePackage"),
                    System.getProperty("typeNamePrefix"),
                    System.getProperty("javaPackage"),
                    System.getProperty("outputFolder")
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void fromDatabase(String oraclePackage, String typeNamePrefix, String javaPackage, String outputFolder) throws SQLException, IOException {
        now(new GetDataFromDatabase().please(oraclePackage, typeNamePrefix), javaPackage, outputFolder);
    }

    public void now(Incoming incoming, String javaPackage, String outputFolder) throws IOException {
        TypeMapperFactory typeMapperFactory = new TypeMapperFactory(incoming.getTypeSpecifications());

        logger.log(Level.INFO, "Generating class for package");
        String packageClassName = new PascalCase().please(new ExtractPackageName().please(incoming.getPackageSpecification()));
        String packageClassCode = new AddPackageStatement(javaPackage).to(
                new GenerateClassCode().please(incoming.getPackageSpecification(), typeMapperFactory));
        save(outputFolder, packageClassName, packageClassCode);
        logger.log(Level.INFO, "-> " + javaPackage + "." + packageClassName);

        logger.log(Level.INFO, "Generating classes for types");
        for (int i = 0; i< incoming.getTypeNames().size(); i++) {
            String typeName = incoming.getTypeNames().get(i);
            String typeClassName = new PascalCase().please(typeName);
            logger.log(Level.INFO, "-> generating " + javaPackage + "." + typeClassName);
            List<String> typeSpecification = incoming.getTypeSpecifications().get(i);
            String typeClassCode = new AddPackageStatement(javaPackage).to(
                    (typeMapperFactory.isArrayType(typeName)) ?
                            new GenerateArrayTypeCode().please(typeSpecification) :
                            new GenerateTypeCode().please(typeSpecification, typeMapperFactory));
            save(outputFolder, typeClassName, typeClassCode);
        }
    }

    public String getLog() {
        return logSink.getLog();
    }

    public LogSink getLogSink() {
        return logSink;
    }
}
