package ericminio.javaoracle.domain;

import ericminio.javaoracle.support.LogSink;
import ericminio.javaoracle.support.PascalCase;
import ericminio.javaoracle.support.Stringify;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GeneratePackageCode {

    private final Logger logger = new LogSink(true).getLogger();
    private String packageName;

    public String please(List<String> packageSpecification, TypeMapperFactory typeMapperFactory) throws IOException {
        logger.log(Level.INFO, "package specification:\n" + new JoinWith("\n").please(packageSpecification));

        String classTemplate = new Stringify().inputStream(this.getClass().getClassLoader().getResourceAsStream("templateForPackage.java"));
        this.packageName = new ExtractPackageName().please(packageSpecification);

        logger.log(Level.INFO, "Generating methods");
        List<String> returnTypes = new ArrayList<>();
        String methodsCode = "";
        List<List<String>> functionSpecifications = new ExtractFunctionSpecifications().please(packageSpecification);
        for (int i=0; i < functionSpecifications.size(); i++){
            List<String> functionSpecification = functionSpecifications.get(i);
            GenerateMethodCode generator = new GenerateMethodCode();
            String methodCode = generator.please(functionSpecification, packageName, typeMapperFactory);
            methodsCode += methodCode + "\n";
            if (i != functionSpecifications.size()-1) {
                methodsCode += "\n";
            }
            returnTypes.add(generator.getReturnType());
        }
        logger.log(Level.INFO, "Building type mapping");
        String typeMapping = new BuildTypeMapping(typeMapperFactory).please(returnTypes);
        if (typeMapping.length() > 0) {
            classTemplate = classTemplate.replace("ClassName(Connection connection) {", "ClassName(Connection connection) throws SQLException {");
        }
        String code = classTemplate
                .replace("ClassName", new PascalCase().please(packageName))
                .replace("        // type mapping\n", typeMapping)
                .replace("    // methods", methodsCode)
        ;
        code = new GenerateImports().please(code);

        return code;
    }

    public String getPackageName() {
        return this.packageName;
    }
}