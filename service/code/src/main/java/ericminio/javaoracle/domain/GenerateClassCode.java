package ericminio.javaoracle.domain;

import ericminio.javaoracle.support.PascalCase;
import ericminio.javaoracle.support.Stringify;

import java.io.IOException;
import java.util.List;

public class GenerateClassCode {

    private String packageName;

    public String please(List<String> packageSpecification) throws IOException {
        String classTemplate = new Stringify().inputStream(this.getClass().getClassLoader().getResourceAsStream("templateForClass.java"));

        this.packageName = new ExtractPackageName().please(packageSpecification);
        String classCode = classTemplate.replace("ClassName", new PascalCase().please(packageName));

        String methodsCode = "";
        List<List<String>> functionSpecifications = new ExtractFunctionSpecifications().please(packageSpecification);
        for (int i=0; i < functionSpecifications.size(); i++){
            List<String> functionSpecification = functionSpecifications.get(i);
            String methodCode = new GenerateMethodCode().please(functionSpecification, packageName);
            methodsCode += methodCode + "\n";
            if (i != functionSpecifications.size()-1) {
                methodsCode += "\n";
            }
        }
        classCode = classCode.replace("    // methods", methodsCode);

        return classCode;
    }

    public String getPackageName() {
        return this.packageName;
    }
}