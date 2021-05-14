package ericminio.javaoracle.domain;

import ericminio.javaoracle.support.CamelCase;
import ericminio.javaoracle.support.Stringify;

import java.io.IOException;
import java.util.List;

public class GenerateMethodCode {

    private String returnType;

    public String please(List<String> functionSpecification, String packageName, TypeMapperFactory typeMapperFactory) throws IOException {
        String methodTemplate = new Stringify().inputStream(this.getClass().getClassLoader().getResourceAsStream("templateForMethod.java"));

        String functionName = new ExtractFunctionName().please(functionSpecification);
        returnType = new ExtractReturnType().please(functionSpecification);
        Parameters parameters = new ExtractParameters().please(functionSpecification);
        String methodCode = methodTemplate
                .replace("public Object", "public " + typeMapperFactory.of(returnType).javaType())
                .replace("methodName()", "methodName(" + new BuildMethodParameterList(typeMapperFactory).please(parameters) + ")")
                .replace("methodName", new CamelCase().please(functionName))
                .replace("packageName", packageName)
                .replace("functionName", functionName)
                .replace("???", new PlaceholderList().please(parameters.count()))
                .replace("        // set IN parameters\n", new BuildSqlStatementParameterSettings(typeMapperFactory).please(parameters))
                .replace("return data;", typeMapperFactory.of(returnType).methodReturnStatement())
                ;
        return methodCode;
    }

    public String getReturnType() {
        return returnType;
    }
}
