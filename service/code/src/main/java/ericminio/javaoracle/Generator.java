package ericminio.javaoracle;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class Generator {

    public String generate(List<String> specification) throws IOException {        
        InputStream stream = this.getClass().getClassLoader().getResourceAsStream("template.java");

        String packageName = new ExtractPackageName().please(specification);
        String functionName = new ExtractFunctionName().please(specification);

        String code = new Stringify().inputStream(stream)
            .replace("ClassName", new ConvertPackageNameIntoClassName().please(packageName))
            .replace("methodName", new ConvertFunctionNameIntoMethodName().please(functionName))
            .replace("packageName", packageName)
            .replace("functionName", functionName)
            ;

        return code;
    }
}