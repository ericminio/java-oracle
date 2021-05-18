package ericminio.javaoracle.data;

import ericminio.javaoracle.domain.Incoming;
import ericminio.javaoracle.domain.JoinWith;
import ericminio.javaoracle.domain.RemoveComments;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BuildIncoming {

    public Incoming from(List<String> packageSpecificationLines, List<String> typesSpecificationsLines) {
        Incoming incoming = new Incoming();

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

        typesSpecificationsLines = new RemoveDropTypeStatements().from(typesSpecificationsLines);
        typesSpecificationsLines = new RemoveComments().please(typesSpecificationsLines);
        String asOneLine = new JoinWith("").please(typesSpecificationsLines);
        String[] statements = asOneLine.split("/");
        List<List<String>> typeSpecifications = new ArrayList<>();
        for (int i=0; i<statements.length; i++) {
            String statement = statements[i];
            typeSpecifications.add(Arrays.asList(statement));
        }
        incoming.setTypeSpecifications(typeSpecifications);

        return incoming;
    }
}
