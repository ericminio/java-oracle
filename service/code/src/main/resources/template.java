
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

public class ClassName {

    private Connection connection;

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public int methodName() throws SQLException {
        CallableStatement statement = connection.prepareCall("{? = call packageName.functionName()}");
        statement.registerOutParameter(1, Types.INTEGER);
        statement.executeUpdate();
        
        return statement.getInt(1);
    }
}