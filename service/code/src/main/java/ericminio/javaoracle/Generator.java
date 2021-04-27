package ericminio.javaoracle;

import java.io.IOException;
import java.util.List;

public class Generator {

    private String packageName;

    public String generate(List<String> packageSpecification) throws IOException {
        String classTemplate = new Stringify().inputStream(this.getClass().getClassLoader().getResourceAsStream("templateForClass.java"));
        String methodTemplate = new Stringify().inputStream(this.getClass().getClassLoader().getResourceAsStream("templateForMethod.java"));

        this.packageName = new ExtractPackageName().please(packageSpecification);
        String classCode = classTemplate.replace("ClassName", new ConvertPackageNameIntoClassName().please(packageName));

        String methods = "";
        List<List<String>> functionSpecifications = new ExtractFunctionSpecifications().please(packageSpecification);
        for (int i=0; i < functionSpecifications.size(); i++){
            List<String> functionSpecification = functionSpecifications.get(i);
            String functionName = new ExtractFunctionName().please(functionSpecification);
            String returnType = new ExtractReturnType().please(functionSpecification);
            Parameters parameters = new ExtractFunctionParameters().please(functionSpecification);
            String methodCode = classTemplate = methodTemplate
                .replace("public int", "public " + new TypeMapper().javaTypeOf(returnType))
                .replace("methodName()", "methodName(" + parameters.toList() + ")")
                .replace("methodName", new ConvertFunctionNameIntoMethodName().please(functionName))
                .replace("packageName", packageName)
                .replace("functionName", functionName)
                .replace("???", new PlaceholderList().please(parameters.size()))
                .replace("getTtt", new TypeMapper().getterOf(returnType))
                .replace("Types.TTT", new TypeMapper().typeOf(returnType))
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