package ericminio.javaoracle.data;

import ericminio.javaoracle.domain.ExtractTypeName;
import ericminio.javaoracle.domain.JoinWith;
import ericminio.javaoracle.domain.RemoveComments;

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
        List<String> packageSpecificationLines = Files.readAllLines(Paths.get(packageFile), StandardCharsets.UTF_8);
        List<String> packageSpecification = new ArrayList<>();
        for (int i=0; i<packageSpecificationLines.size(); i++) {
            String candidate = packageSpecificationLines.get(i).trim();
            if (candidate.equalsIgnoreCase("/")) {
                break;
            }
            else {
                packageSpecification.add(candidate);
            }
        }
        incoming.setPackageSpecification(packageSpecification);

        List<List<String>> typeSpecifications = new ArrayList<>();
        List<String> typeNames = new ArrayList<>();
        List<String> lines = Files.readAllLines(Paths.get(typesFile), StandardCharsets.UTF_8);
        lines = removeDropTypeStatements(lines);
        lines = new RemoveComments().please(lines);
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

    private List<String> removeDropTypeStatements(List<String> lines) {
        List<String> clean = new ArrayList<>();
        for (int i=0; i<lines.size(); i++) {
            String candidate = lines.get(i).trim().toLowerCase();
            if (! candidate.startsWith("drop type")) {
                clean.add(candidate);
            }
        }
        return clean;
    }
}
