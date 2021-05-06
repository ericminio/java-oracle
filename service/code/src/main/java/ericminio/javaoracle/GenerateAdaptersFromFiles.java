package ericminio.javaoracle;

import ericminio.javaoracle.data.GetDataFromFiles;
import ericminio.javaoracle.data.Incoming;
import ericminio.javaoracle.domain.*;
import ericminio.javaoracle.support.LogSink;
import ericminio.javaoracle.support.PascalCase;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import static ericminio.javaoracle.support.FileUtils.save;

public class GenerateAdaptersFromFiles {

    private final LogSink logSink;
    private final Logger logger;

    public GenerateAdaptersFromFiles() {
        this.logSink = new LogSink(true);
        this.logger = logSink.getLogger();
    }

    public static void main(String[] args) {
        GenerateAdaptersFromFiles generateAdaptersFromFiles = new GenerateAdaptersFromFiles();
        try {
            generateAdaptersFromFiles.fromFiles(
                    System.getProperty("packageFile"),
                    System.getProperty("typesFile"),
                    System.getProperty("javaPackage"),
                    System.getProperty("outputFolder")
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void fromFiles(String packageFile, String typesFile, String javaPackage, String outputFolder) throws SQLException, IOException {
        now(new GetDataFromFiles().please(packageFile, typesFile), javaPackage, outputFolder);
    }

    public void now(Incoming incoming, String javaPackage, String outputFolder) throws IOException {
        TypeMapperFactory typeMapperFactory = new TypeMapperFactory(incoming.getTypeSpecifications());

        logger.log(Level.INFO, "Generating class for package");
        String packageClassName = new PascalCase().please(new ExtractPackageName().please(incoming.getPackageSpecification()));
        String packageClassCode = new AddPackageStatement(javaPackage).to(
                new GeneratePackageCode().please(incoming.getPackageSpecification(), typeMapperFactory));
        save(outputFolder, packageClassName, packageClassCode);
        logger.log(Level.INFO, "-> " + javaPackage + "." + packageClassName);

        logger.log(Level.INFO, "Generating classes for types");
        for (int i = 0; i< incoming.getTypeNames().size(); i++) {
            String typeName = incoming.getTypeNames().get(i);
            String typeClassName = new PascalCase().please(typeName);
            logger.log(Level.INFO, "-> generating " + javaPackage + "." + typeClassName);
            String typeClassCode = new AddPackageStatement(javaPackage).to(
                    (typeMapperFactory.isArrayType(typeName)) ?
                            new GenerateArrayTypeCode().please(incoming.getTypeSpecifications().get(i)) :
                            new GenerateTypeCode().please(incoming.getTypeSpecifications().get(i), typeMapperFactory));
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
