package ericminio.javaoracle.data;

import ericminio.javaoracle.http.FormDataSet;

import java.util.Arrays;
import java.util.List;

public class GenerateDataFromWeb {

    public Incoming please(FormDataSet formDataSet) {
        String[] packageLines = formDataSet.getByName("package").getValue().split("\n");
        String[] typesLines = formDataSet.getByName("types").getValue().split("\n");
        List<String> packageSpecificationLines = Arrays.asList(packageLines);
        List<String> typesSpecificationsLines = Arrays.asList(typesLines);

        return new BuildIncoming().from(packageSpecificationLines, typesSpecificationsLines);
    }
}
