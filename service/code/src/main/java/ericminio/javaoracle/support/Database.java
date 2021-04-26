package ericminio.javaoracle.support;

import oracle.jdbc.pool.OracleDataSource;

import java.sql.Connection;

public class Database {

    public Connection connection() throws Exception {
        OracleDataSource dataSource = new OracleDataSource();
        dataSource.setURL("jdbc:oracle:thin:@"+ new Environment().getOracleHost() + ":1521:XE");
        dataSource.setUser("system");
        dataSource.setPassword("oracle");

        return dataSource.getConnection();
    }
}
