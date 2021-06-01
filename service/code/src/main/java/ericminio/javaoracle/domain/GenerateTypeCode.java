package ericminio.javaoracle.domain;

import ericminio.javaoracle.support.PascalCase;
import ericminio.javaoracle.support.Stringify;

import java.io.IOException;
import java.util.List;

public class GenerateTypeCode {

    public String please(List<String> typeSpecification, TypeMapperFactory typeMapperFactory) throws IOException {
        String typeTemplate = new Stringify().inputStream(this.getClass().getClassLoader().getResourceAsStream("templateForType.java"));
        String dateUtilTemplate = new Stringify().inputStream(this.getClass().getClassLoader().getResourceAsStream("templateForDateUtil.java"));

        String typeName = new ExtractTypeName().please(typeSpecification);
        Parameters parameters = new ExtractParameters().please(typeSpecification);
        String code = typeTemplate
                .replace("ClassName", new PascalCase().please(typeName))
                .replace("STATIC_NAME_FIELD", typeName.toUpperCase())
                .replace("    // fields declaration", new BuildDeclarationStatements(typeMapperFactory).please(parameters))
                .replace("    // fields accessors", new BuildAccessors(typeMapperFactory).please(parameters))
                .replace("                false // fields equals contribution", new BuildEqualsReturnValue(typeMapperFactory).please(parameters))
                .replace("                0 // fields hashCode contribution", new BuildHashcodeReturnValue(typeMapperFactory).please(parameters))
                .replace("                // fields toString contribution", new BuildToStringConcatenation(typeMapperFactory).please(parameters))
                .replace("        // fields readSQL contribution", new BuildReadSql(typeMapperFactory).please(parameters))
                .replace("        // fields writeSQL contribution", new BuildWriteSql(typeMapperFactory).please(parameters))
                .replace("    // date util if needed\n", parameters.hasDateField() ? dateUtilTemplate : "")
        ;

        if (code.indexOf("Clob ") != -1) {
            code = "import java.sql.Clob;\n" + code;
        }
        if (code.indexOf("BigDecimal ") != -1) {
            code = "import java.math.BigDecimal;\n" + code;
        }
        if (code.indexOf("Date ") != -1) {
            code = code.replace("import java.sql.SQLOutput;\n", "import java.sql.SQLOutput;\nimport java.util.Date;\n");
        }

        return code;
    }
}