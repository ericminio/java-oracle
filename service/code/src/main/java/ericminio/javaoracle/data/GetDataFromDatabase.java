package ericminio.javaoracle.data;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GetDataFromDatabase {

    public Incoming please(String oraclePackage, String typeNamePrefix) throws SQLException {
        Incoming incoming = new Incoming();
        incoming.setPackageSpecification(new Database().selectPackageDefinition(oraclePackage));
        List<String> typeNames = new Database().selectDistinctTypeNamesWithPrefix(typeNamePrefix);
        List<List<String>> typeSpecifications = new ArrayList<>();
        for (String type:typeNames) {
            List<String> typeSpecification = new Database().selectTypeDefinition(type);
            typeSpecifications.add(typeSpecification);
        }
        incoming.setTypeSpecifications(typeSpecifications);
        incoming.setTypeNames(typeNames);
        return incoming;
    }
}
