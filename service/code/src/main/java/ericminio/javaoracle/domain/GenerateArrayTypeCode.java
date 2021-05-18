package ericminio.javaoracle.domain;

import ericminio.javaoracle.support.PascalCase;
import ericminio.javaoracle.support.Stringify;

import java.io.IOException;
import java.util.List;

public class GenerateArrayTypeCode {

    public String please(List<String> typeSpecification, TypeMapperFactory typeMapperFactory) throws IOException {
        String template = new Stringify().inputStream(this.getClass().getClassLoader().getResourceAsStream("templateForArrayType.java"));

        String arrayTypeName = new ExtractTypeName().please(typeSpecification);
        String recordTypeName = new ExtractRecordTypeName().please(typeSpecification);
        String javaRecordTypeName = typeMapperFactory.of(recordTypeName).javaType();
        String code = template
                .replace("ArrayType", new PascalCase().please(arrayTypeName))
                .replace("RecordType", javaRecordTypeName)
                ;

        return code;
    }
}