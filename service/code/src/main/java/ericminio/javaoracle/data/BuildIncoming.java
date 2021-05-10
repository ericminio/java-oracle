package ericminio.javaoracle.data;

import ericminio.javaoracle.domain.ExtractTypeName;
import ericminio.javaoracle.domain.JoinWith;
import ericminio.javaoracle.domain.RemoveComments;
import ericminio.javaoracle.http.FormDataSet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BuildIncoming {

    public Incoming from(FormDataSet formDataSet) {
        Incoming incoming = new Incoming();

        String[] packageLines = formDataSet.getByName("package").getValue().split("\n");
        List<String> packageSpecificationLines = Arrays.asList(packageLines);
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

        String[] typesLines = formDataSet.getByName("types").getValue().split("\n");
        List<String> typesSpecificationsLines = Arrays.asList(typesLines);
        List<List<String>> typeSpecifications = new ArrayList<>();
        List<String> typeNames = new ArrayList<>();
        typesSpecificationsLines = removeDropTypeStatements(typesSpecificationsLines);
        typesSpecificationsLines = new RemoveComments().please(typesSpecificationsLines);
        String asOneLine = new JoinWith("").please(typesSpecificationsLines);
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
