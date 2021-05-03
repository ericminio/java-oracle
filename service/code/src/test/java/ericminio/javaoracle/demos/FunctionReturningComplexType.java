package ericminio.javaoracle.demos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FunctionReturningComplexType {

    private Connection connection;

    public FunctionReturningComplexType(Connection connection) throws SQLException {
        this.connection = connection;
        connection.getTypeMap().put(ComplexType.NAME, ComplexType.class);
        connection.getTypeMap().put(CustomType.NAME, CustomType.class);
    }

    public ComplexType getField() throws SQLException {
        PreparedStatement statement = connection.prepareStatement("select returning_complex_type.get_field() from dual");
        ResultSet resultSet = statement.executeQuery();
        resultSet.next();

        return (ComplexType) resultSet.getObject(1);
    }
}
