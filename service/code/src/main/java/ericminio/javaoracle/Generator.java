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
            String functionName = new ExtractFunctionName().please(functionSpecifications.get(i));
            String methodCode = classTemplate = methodTemplate
                .replace("methodName", new ConvertFunctionNameIntoMethodName().please(functionName))
                .replace("packageName", packageName)
                .replace("functionName", functionName);
            methods += methodCode;
        }
        classCode = classCode.replace("    // methods", methods);

        return classCode;
    }
}