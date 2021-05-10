package ericminio.javaoracle.domain;

import ericminio.javaoracle.data.Incoming;
import ericminio.javaoracle.support.LogSink;
import ericminio.javaoracle.support.PascalCase;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GenerateClasses {

    private final LogSink logSink;
    private final Logger logger;

    public GenerateClasses() {
        this.logSink = new LogSink(true);
        this.logger = logSink.getLogger();
    }

    public FileSet from(Incoming incoming, String javaPackage) throws IOException {
        FileSet fileSet = new FileSet();
        TypeMapperFactory typeMapperFactory = new TypeMapperFactory(incoming.getTypeSpecifications());

        logger.log(Level.INFO, "Generating class for package");
        String packageClassName = new PascalCase().please(new ExtractPackageName().please(incoming.getPackageSpecification()));
        String packageClassCode = new AddPackageStatement(javaPackage).to(
                new GeneratePackageCode().please(incoming.getPackageSpecification(), typeMapperFactory));
        fileSet.add(new FileInfo(packageClassName+".java", packageClassCode));
        logger.log(Level.INFO, "-> " + javaPackage + "." + packageClassName);

        logger.log(Level.INFO, "Generating classes for types");
        for (int i = 0; i< incoming.getTypeNames().size(); i++) {
            String typeName = incoming.getTypeNames().get(i);
            logger.log(Level.INFO, "-> generating class for type " + typeName);
            String typeClassName = new PascalCase().please(typeName);
            String typeClassCode = new AddPackageStatement(javaPackage).to(
                    (typeMapperFactory.isArrayType(typeName)) ?
                            new GenerateArrayTypeCode().please(incoming.getTypeSpecifications().get(i)) :
                            new GenerateTypeCode().please(incoming.getTypeSpecifications().get(i), typeMapperFactory));
            fileSet.add(new FileInfo(typeClassName+".java", typeClassCode));
        }
        return fileSet;
    }

    public String getLog() {
        return this.logSink.getLog();
    }

    public LogSink getLogSink() {
        return this.logSink;
    }
}
