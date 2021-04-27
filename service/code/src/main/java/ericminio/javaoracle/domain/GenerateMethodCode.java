package ericminio.javaoracle.domain;

import ericminio.javaoracle.support.CamelCase;
import ericminio.javaoracle.support.Stringify;

import java.io.IOException;
import java.util.List;

public class GenerateMethodCode {

    public String please(List<String> functionSpecification, String packageName) throws IOException {
        String methodTemplate = new Stringify().inputStream(this.getClass().getClassLoader().getResourceAsStream("templateForMethod.java"));

        String functionName = new ExtractFunctionName().please(functionSpecification);
        String returnType = new ExtractReturnType().please(functionSpecification);
        Parameters parameters = new ExtractFunctionParameters().please(functionSpecification);
        String methodCode = methodTemplate
                .replace("public int", "public " + new TypeMapperFactory().of(returnType).javaType())
                .replace("methodName()", "methodName(" + parameters.toList() + ")")
                .replace("methodName", new CamelCase().please(functionName))
                .replace("packageName", packageName)
                .replace("functionName", functionName)
                .replace("???", new PlaceholderList().please(parameters.size()))
                .replace("        // set IN parameters\n", parameters.getParametersSettings())
                .replace("getTtt", new TypeMapperFactory().of(returnType).getter())
                ;
        return methodCode;
    }
}
