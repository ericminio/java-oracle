package ericminio.javaoracle.data;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GetDataFromDatabase {

    public Incoming please(String oraclePackage, String typeNamePrefix) throws SQLException {
        List<String> packageSpecification = new Database().selectPackageDefinition(oraclePackage);
        List<String> typeNames = new Database().selectDistinctTypeNamesWithPrefix(typeNamePrefix);
        List<String> typeSpecifications = new ArrayList<>();
        for (String type:typeNames) {
            List<String> typeSpecification = new Database().selectTypeDefinition(type);
            typeSpecifications.addAll(typeSpecification);
            typeSpecifications.add("/\n");
        }

        return new BuildIncoming().from(packageSpecification, typeSpecifications);
    }
}
