package ericminio.javaoracle.main;

import ericminio.javaoracle.data.GetDataFromFiles;

import java.io.IOException;
import java.sql.SQLException;

public class GenerateAdaptersFromFiles extends GenerateAdapters {

    public static void main(String[] args) {
        GenerateAdaptersFromFiles generateAdaptersFromFiles = new GenerateAdaptersFromFiles();
        try {
            generateAdaptersFromFiles.fromFiles(
                    System.getProperty("packageFile"),
                    System.getProperty("typesFile"),
                    System.getProperty("javaPackage"),
                    System.getProperty("outputFolder")
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void fromFiles(String packageFile, String typesFile, String javaPackage, String outputFolder) throws SQLException, IOException {
        now(new GetDataFromFiles().please(packageFile, typesFile), javaPackage, outputFolder);
    }
}
