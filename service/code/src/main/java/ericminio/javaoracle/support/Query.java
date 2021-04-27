package ericminio.javaoracle.support;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Query {

    private Connection connection;

    public Query(Connection connection) {
        this.connection = connection;
    }

    public static Query with(Connection connection) {
        return new Query(connection);
    }

    public void execute(String sql) {
        Statement statement = null;
        try {
            statement = connection.createStatement();
            statement.executeUpdate(sql);
        }
        catch (SQLException executing) {
            throw new RuntimeException(executing.getMessage() + " " + sql);
        }
        finally {
            try {
                statement.close();
            }
            catch (SQLException closing) {
                throw new RuntimeException(closing.getMessage());
            }
        }
    }

    public String selectString(String sql) {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery(sql);
            resultSet.next();
            return resultSet.getString(1);
        }
        catch (SQLException executing) {
            throw new RuntimeException(executing.getMessage() + " " + sql);
        }
        finally {
            try {
                statement.close();
            }
            catch (SQLException closing) {
                throw new RuntimeException(closing.getMessage());
            }
        }
    }

    public List<String> selectPackageDefinition(String packageName) {
        String sql = "select text from all_source where type='PACKAGE' and name=? order by line";
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(sql);
            statement.setString(1, packageName.toUpperCase());
            ResultSet resultSet = statement.executeQuery();
            List<String> strings = new ArrayList<>();
            while (resultSet.next()) {
                String line = resultSet.getString(1);
                strings.add(line);
            }
            return strings;
        }
        catch (SQLException executing) {
            throw new RuntimeException(executing.getMessage() + " " + sql);
        }
        finally {
            try {
                statement.close();
            }
            catch (SQLException closing) {
                throw new RuntimeException(closing.getMessage());
            }
        }
    }
}
