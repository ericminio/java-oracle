package ericminio.javaoracle.data;

import ericminio.javaoracle.support.LogSink;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GetDataFromDatabase {

    private Logger logger = new LogSink(true).getLogger();

    public Incoming please(String oraclePackage, String typeNamePrefix) throws SQLException {
        logger.log(Level.INFO, "Fetching package definition");
        List<String> packageSpecification = new Database().selectPackageDefinition(oraclePackage);
        logger.log(Level.INFO, "Fetching type names");
        List<String> typeNames = new Database().selectDistinctTypeNamesWithPrefix(typeNamePrefix);
        logger.log(Level.INFO, "Fetching type definitions");
        List<String> typeSpecifications = new ArrayList<>();
        for (String type:typeNames) {
            List<String> typeSpecification = new Database().selectTypeDefinition(type);
            typeSpecifications.addAll(typeSpecification);
            typeSpecifications.add("/\n");
        }
        logger.log(Level.INFO, typeNames.size() + " type definitions fetched");

        return new BuildIncoming().from(packageSpecification, typeSpecifications);
    }
}
