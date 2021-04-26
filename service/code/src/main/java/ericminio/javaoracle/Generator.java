package ericminio.javaoracle;

import java.io.IOException;
import java.util.List;

public class Generator {

    public String generate(List<String> packageSpecification) throws IOException {
        String classTemplate = new Stringify().inputStream(this.getClass().getClassLoader().getResourceAsStream("templateForClass.java"));
        String methodTemplate = new Stringify().inputStream(this.getClass().getClassLoader().getResourceAsStream("templateForMethod.java"));

        String packageName = new ExtractPackageName().please(packageSpecification);
        String classCode = classTemplate.replace("ClassName", new ConvertPackageNameIntoClassName().please(packageName));

        String methods = "";
        List<List<String>> functionSpecifications = new ExtractFunctionSpecifications().please(packageSpecification);
        for (int i=0; i < functionSpecifications.size(); i++){
            List<String> functionSpecification = functionSpecifications.get(i);
            String functionName = new ExtractFunctionName().please(functionSpecification);
            String returnType = new ExtractReturnType().please(functionSpecification);
            String methodCode = classTemplate = methodTemplate
                .replace("methodName", new ConvertFunctionNameIntoMethodName().please(functionName))
                .replace("packageName", packageName)
                .replace("functionName", functionName)
                .replace("Types.TTT", new TypeMapper().typeOf(returnType))
                .replace("getTtt", new TypeMapper().accessorOf(returnType))
                    ;
            methods += methodCode;
        }
        classCode = classCode.replace("    // methods", methods);

        return classCode;
    }
}