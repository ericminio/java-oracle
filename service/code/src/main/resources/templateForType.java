
import java.sql.SQLData;
import java.sql.SQLException;
import java.sql.SQLInput;
import java.sql.SQLOutput;

public class ClassName implements SQLData {
    public static final String NAME = "STATIC_NAME_FIELD";
    // fields

    public ClassName() {}

    // accessors

    @Override
    public boolean equals(Object o) {
        if (! (o instanceof ClassName)) {
            return false;
        }
        ClassName other = (ClassName) o;

        return
                false // EQUALS_RETURN_VALUE
                ;
    }

    @Override
    public int hashCode() {
        return
                0 // HASHCODE_RETURN_VALUE
                ;
    }
}