package ericminio.javaoracle.data;

import ericminio.javaoracle.domain.Incoming;
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
        for (int i=0; i<typeNames.size(); i++) {
            String type = typeNames.get(i);
            logger.log(Level.INFO, (i+1) + "/" + typeNames.size() +" fetching type " + type);
            List<String> typeSpecification = new Database().selectTypeDefinition(type);
            typeSpecifications.addAll(typeSpecification);
            typeSpecifications.add("/\n");
        }

        return new BuildIncoming().from(packageSpecification, typeSpecifications);
    }
}
