import java.sql.SQLData;
import java.sql.SQLException;
import java.sql.SQLInput;
import java.sql.SQLOutput;

public class ClassName implements SQLData {
    public static final String NAME = "STATIC_NAME_FIELD";
    // fields declaration

    public ClassName() {}

    // fields accessors

    @Override
    public boolean equals(Object o) {
        if (! (o instanceof ClassName)) {
            return false;
        }
        ClassName other = (ClassName) o;

        return
                false // fields equals contribution
                ;
    }

    @Override
    public int hashCode() {
        return
                0 // fields hashCode contribution
                ;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "["
                // fields toString contribution
                + " ]";
    }

    @Override
    public String getSQLTypeName() {
        return NAME;
    }

    @Override
    public void readSQL(SQLInput stream, String typeName) throws SQLException {
        // fields readSQL contribution
    }

    @Override
    public void writeSQL(SQLOutput stream) throws SQLException {
        // fields writeSQL contribution
    }

    // date util if needed
}
