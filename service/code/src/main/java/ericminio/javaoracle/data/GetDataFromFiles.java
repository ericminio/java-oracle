package ericminio.javaoracle.data;

import ericminio.javaoracle.domain.ExtractTypeName;
import ericminio.javaoracle.domain.JoinWith;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GetDataFromFiles {

    public Incoming please(String packageFile, String typesFile) throws IOException {
        Incoming incoming = new Incoming();
        incoming.setPackageSpecification(Files.readAllLines(Paths.get(packageFile), StandardCharsets.UTF_8));

        List<List<String>> typeSpecifications = new ArrayList<>();
        List<String> typeNames = new ArrayList<>();
        List<String> lines = Files.readAllLines(Paths.get(typesFile), StandardCharsets.UTF_8);
        String asOneLine = new JoinWith("").please(lines);
        String[] statements = asOneLine.split("/");
        for (int i=0; i<statements.length; i++) {
            String statement = statements[i];
            typeSpecifications.add(Arrays.asList(statement));
            typeNames.add(new ExtractTypeName().please(Arrays.asList(statement)));
        }
        incoming.setTypeSpecifications(typeSpecifications);
        incoming.setTypeNames(typeNames);

        return incoming;
    }
}
