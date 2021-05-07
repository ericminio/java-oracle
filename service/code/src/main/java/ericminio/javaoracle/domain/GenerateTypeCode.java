package ericminio.javaoracle.domain;

import ericminio.javaoracle.support.PascalCase;
import ericminio.javaoracle.support.Stringify;

import java.io.IOException;
import java.util.List;

public class GenerateTypeCode {

    public String please(List<String> typeSpecification, TypeMapperFactory typeMapperFactory) throws IOException {
        String typeTemplate = new Stringify().inputStream(this.getClass().getClassLoader().getResourceAsStream("templateForType.java"));

        String typeName = new ExtractTypeName().please(typeSpecification);
        Parameters parameters = new ExtractParameters().please(typeSpecification);
        String code = typeTemplate
                .replace("ClassName", new PascalCase().please(typeName))
                .replace("STATIC_NAME_FIELD", typeName.toUpperCase())
                .replace("    // fields declaration", new BuildDeclarationStatements().please(parameters))
                .replace("    // fields accessors", new BuildAccessors().please(parameters))
                .replace("                false // fields equals contribution", new BuildEqualsReturnValue().please(parameters))
                .replace("                0 // fields hashCode contribution", new BuildHashcodeReturnValue().please(parameters))
                .replace("                // fields toString contribution", new BuildToStringConcatenation().please(parameters))
                .replace("        // fields readSQL contribution", new BuildReadSql(typeMapperFactory).please(parameters))
                .replace("        // fields writeSQL contribution", new BuildWriteSql(typeMapperFactory).please(parameters))
        ;

        if (code.indexOf("BigDecimal ") != -1) {
            code = "import java.math.BigDecimal;\n" + code;
        }
        if (code.indexOf("Date ") != -1) {
            code = code.replace("import java.sql.SQLOutput;", "import java.sql.SQLOutput;\nimport java.util.Date;");
        }

        return code;
    }
}