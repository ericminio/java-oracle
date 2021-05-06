package ericminio.javaoracle;

import ericminio.javaoracle.data.GetDataFromDatabase;

import java.io.IOException;
import java.sql.SQLException;

public class GenerateAdaptersFromDatabase extends GenerateAdapters {

    public static void main(String[] args) {
        GenerateAdaptersFromDatabase generateAdaptersFromDatabase = new GenerateAdaptersFromDatabase();
        try {
            generateAdaptersFromDatabase.fromDatabase(
                    System.getProperty("oraclePackage"),
                    System.getProperty("typeNamePrefix"),
                    System.getProperty("javaPackage"),
                    System.getProperty("outputFolder")
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void fromDatabase(String oraclePackage, String typeNamePrefix, String javaPackage, String outputFolder) throws SQLException, IOException {
        now(new GetDataFromDatabase().please(oraclePackage, typeNamePrefix), javaPackage, outputFolder);
    }
}
