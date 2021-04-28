package ericminio.javaoracle.data;

import ericminio.javaoracle.support.Environment;
import oracle.jdbc.pool.OracleDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

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
        List<String> definition = Query.with(connection).selectStrings(
                "select text from all_source where type='TYPE' and name=? order by line", typeName.toUpperCase());
        connection.close();
        return definition;
    }

    public List<String> selectPackageDefinition(String oraclePackage) throws SQLException {
        Connection connection = connection();
        List<String> definition = Query.with(connection).selectStrings(
                "select text from all_source where type='PACKAGE' and name=? order by line", oraclePackage.toUpperCase());
        connection.close();
        return definition;
    }
}
