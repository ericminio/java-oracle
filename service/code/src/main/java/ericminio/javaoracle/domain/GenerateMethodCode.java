package ericminio.javaoracle.domain;

import ericminio.javaoracle.support.CamelCase;
import ericminio.javaoracle.support.LogSink;
import ericminio.javaoracle.support.Stringify;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GenerateMethodCode {

    private final Logger logger = new LogSink(true).getLogger();
    private String returnType;

    public String please(List<String> functionSpecification, String packageName, TypeMapperFactory typeMapperFactory) throws IOException {
        String methodTemplate = new Stringify().inputStream(this.getClass().getClassLoader().getResourceAsStream("templateForMethod.java"));

        String functionName = new ExtractFunctionName().please(functionSpecification);
        returnType = new ExtractReturnType().please(functionSpecification);
        Parameters parameters = new ExtractParameters().please(functionSpecification);
        logger.log(Level.INFO, ".. generating for " + functionName + " returning "+ returnType);
        String methodCode = methodTemplate
                .replace("public Object", "public " + typeMapperFactory.of(returnType).javaType())
                .replace("methodName()", "methodName(" + new BuildMethodParameterList(typeMapperFactory).please(parameters) + ")")
                .replace("methodName", new CamelCase().please(functionName))
                .replace("packageName", packageName)
                .replace("functionName", functionName)
                .replace("???", new PlaceholderList().please(parameters.count()))
                .replace("Types.OTHER", typeMapperFactory.of(returnType).functionParameterOutType())
                .replace("        // set/register parameters\n", new BuildSqlStatementParameterSettings(typeMapperFactory).please(parameters))
                .replace("        // set out parameters\n", new BuildOutParameterSettings(typeMapperFactory).please(parameters))
                .replace("return data;", typeMapperFactory.of(returnType).methodReturnStatement())
                ;
        return methodCode;
    }

    public String getReturnType() {
        return returnType;
    }
}
