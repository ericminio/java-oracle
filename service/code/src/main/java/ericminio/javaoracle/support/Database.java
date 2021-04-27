package ericminio.javaoracle.support;

import oracle.jdbc.pool.OracleDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class Database {

    private OracleDataSource dataSource;

    public Connection connection() throws Exception {
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
}
