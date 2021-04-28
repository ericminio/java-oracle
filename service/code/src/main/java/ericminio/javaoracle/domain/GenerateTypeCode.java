package ericminio.javaoracle.domain;

import ericminio.javaoracle.support.PascalCase;
import ericminio.javaoracle.support.Stringify;

import java.io.IOException;
import java.util.List;

public class GenerateTypeCode {

    public String please(List<String> typeSpecification) throws IOException {
        String typeTemplate = new Stringify().inputStream(this.getClass().getClassLoader().getResourceAsStream("templateForType.java"));
        String typeCode = typeTemplate;

        String typeName = new ExtractTypeName().please(typeSpecification);
        Parameters parameters = new ExtractParameters().please(typeSpecification);
        typeCode = typeCode
                .replace("ClassName", new PascalCase().please(typeName))
                .replace("STATIC_NAME_FIELD", typeName.toUpperCase())
                .replace("    // fields", new BuildFieldDeclarationStatements().please(parameters))
        ;

        return typeCode;
    }
}