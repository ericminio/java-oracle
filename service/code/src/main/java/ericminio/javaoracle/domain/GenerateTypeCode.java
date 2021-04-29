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
                .replace("    // fields declaration", new BuildFieldDeclarationStatements().please(parameters))
                .replace("    // fields accessors", new BuildFieldAccessors().please(parameters))
                .replace("                false // fields equals contribution", new BuildEqualsReturnValue().please(parameters))
                .replace("                0 // fields hashCode contribution", new BuildHashcodeReturnValue().please(parameters))
                .replace("                // fields toString contribution", new BuildToStringConcatenation().please(parameters))
                .replace("        // fields readSQL contribution", new BuildReadSql().please(parameters))
        ;

        return typeCode;
    }
}