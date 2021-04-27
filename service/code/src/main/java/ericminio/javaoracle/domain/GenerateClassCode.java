package ericminio.javaoracle.domain;

import ericminio.javaoracle.support.CamelCase;
import ericminio.javaoracle.support.PascalCase;
import ericminio.javaoracle.support.Stringify;

import java.io.IOException;
import java.util.List;

public class GenerateClassCode {

    private String packageName;

    public String please(List<String> packageSpecification) throws IOException {
        String classTemplate = new Stringify().inputStream(this.getClass().getClassLoader().getResourceAsStream("templateForClass.java"));
        String methodTemplate = new Stringify().inputStream(this.getClass().getClassLoader().getResourceAsStream("templateForMethod.java"));

        this.packageName = new ExtractPackageName().please(packageSpecification);
        String classCode = classTemplate.replace("ClassName", new PascalCase().please(packageName));

        String methods = "";
        List<List<String>> functionSpecifications = new ExtractFunctionSpecifications().please(packageSpecification);
        for (int i=0; i < functionSpecifications.size(); i++){
            List<String> functionSpecification = functionSpecifications.get(i);
            String functionName = new ExtractFunctionName().please(functionSpecification);
            String returnType = new ExtractReturnType().please(functionSpecification);
            Parameters parameters = new ExtractFunctionParameters().please(functionSpecification);
            String methodCode = classTemplate = methodTemplate
                .replace("public int", "public " + new TypeMapperFactory().of(returnType).javaType())
                .replace("methodName()", "methodName(" + parameters.toList() + ")")
                .replace("methodName", new CamelCase().please(functionName))
                .replace("packageName", packageName)
                .replace("functionName", functionName)
                .replace("???", new PlaceholderList().please(parameters.size()))
                .replace("getTtt", new TypeMapperFactory().of(returnType).getter())
                .replace("Types.TTT", new TypeMapperFactory().of(returnType).sqlType())
                .replace("        // set IN parameters\n", parameters.getParametersSettings())
                    ;
            methods += methodCode + "\n";
            if (i != functionSpecifications.size()-1) {
                methods += "\n";
            }
        }
        classCode = classCode.replace("    // methods", methods);

        return classCode;
    }

    public String getPackageName() {
        return this.packageName;
    }
}