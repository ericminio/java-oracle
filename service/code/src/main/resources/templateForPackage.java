import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

public class ClassName {

    private Connection connection;

    public ClassName() {
    }

    public ClassName(Connection connection) {
        this.setConnection(connection);
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
        // type mapping
    }

    // methods
}
