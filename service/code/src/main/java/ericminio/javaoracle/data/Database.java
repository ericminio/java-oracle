package ericminio.javaoracle.data;

import ericminio.javaoracle.support.Environment;
import oracle.jdbc.pool.OracleDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static ericminio.javaoracle.data.Query.with;

public class Database {

    private OracleDataSource dataSource;

    public Connection connection() throws SQLException {
        return getDataSource().getConnection();
    }

    private OracleDataSource getDataSource() throws SQLException {
        if (this.dataSource == null) {
            this.dataSource = new OracleDataSource();
            this.dataSource.setURL("jdbc:oracle:thin:@" + new Environment().getOracleHost() + ":1521:XE");
            this.dataSource.setUser("system");
            this.dataSource.setPassword("oracle");
        }
        return dataSource;
    }

    public List<String> selectTypeDefinition(String typeName) throws SQLException {
        Connection connection = connection();
        List<String> definition = with(connection).selectStrings(
                "select text from all_source where type='TYPE' and name=? order by line", typeName.toUpperCase());
        connection.close();
        return definition;
    }

    public List<String> selectPackageDefinition(String oraclePackage) throws SQLException {
        Connection connection = connection();
        List<String> definition = with(connection).selectStrings(
                "select text from all_source where type='PACKAGE' and name=? order by line", oraclePackage.toUpperCase());
        connection.close();
        return definition;
    }

    public List<String> selectDistinctTypeNamesWithPrefix(String typeNamePrefix) throws SQLException {
        Connection connection = connection();
        List<String> names = with(connection).selectStrings(
                "select distinct name from all_source where type='TYPE' and name like ?", typeNamePrefix.toUpperCase()+"%");
        connection.close();
        return names;
    }
}
