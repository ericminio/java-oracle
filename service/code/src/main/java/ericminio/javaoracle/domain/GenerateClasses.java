package ericminio.javaoracle.domain;

import ericminio.javaoracle.data.Incoming;
import ericminio.javaoracle.support.PascalCase;

import java.io.IOException;

public class GenerateClasses {

    public FileSet from(Incoming incoming, String javaPackage) throws IOException {
        FileSet fileSet = new FileSet();
        TypeMapperFactory typeMapperFactory = new TypeMapperFactory(incoming.getTypeSpecifications());

        String packageClassName = new PascalCase().please(new ExtractPackageName().please(incoming.getPackageSpecification()));
        String packageClassCode = new AddPackageStatement(javaPackage).to(
                new GeneratePackageCode().please(incoming.getPackageSpecification(), typeMapperFactory));
        fileSet.add(new FileInfo(packageClassName+".java", packageClassCode));

        for (int i = 0; i< incoming.getTypeNames().size(); i++) {
            String typeName = incoming.getTypeNames().get(i);
            String typeClassName = new PascalCase().please(typeName);
            String typeClassCode = new AddPackageStatement(javaPackage).to(
                    (typeMapperFactory.isArrayType(typeName)) ?
                            new GenerateArrayTypeCode().please(incoming.getTypeSpecifications().get(i)) :
                            new GenerateTypeCode().please(incoming.getTypeSpecifications().get(i), typeMapperFactory));
            fileSet.add(new FileInfo(typeClassName+".java", typeClassCode));
        }
        return fileSet;
    }
}
