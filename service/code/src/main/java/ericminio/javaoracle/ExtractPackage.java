package ericminio.javaoracle;

import ericminio.javaoracle.support.Database;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ExtractPackage {

    public static void main(String[] args) {
        ExtractPackage extractPackage = new ExtractPackage();
        try {
            extractPackage.go(
                    System.getProperty("oraclePackage"),
                    System.getProperty("javaPackage"),
                    System.getProperty("outputFolder")
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void debug(String message) {
        System.out.println(message);
    }

    public void go(String oraclePackage, String javaPackage, String outputFolder) throws Exception {
        Connection connection = new Database().connection();
        PreparedStatement statement = connection.prepareStatement("select text from all_source where type='PACKAGE' and name=? order by line");
        statement.setString(1, oraclePackage.toUpperCase());
        ResultSet resultSet = statement.executeQuery();

        List<String> packageSpecification = new ArrayList<>();
        debug("Package specification:");
        while (resultSet.next()) {
            String line = resultSet.getString(1);
            debug(line.trim());
            packageSpecification.add(line);
        }

        Generator generator = new Generator();
        String code = "package " + javaPackage + ";\n" + generator.generate(packageSpecification);
        Path path = Paths.get(outputFolder, new Capitalize().please(generator.getPackageName())+".java");
        Files.write(path, code.getBytes());
    }
}
