package ericminio.javaoracle.demos;

import java.sql.SQLData;
import java.sql.SQLException;
import java.sql.SQLInput;
import java.sql.SQLOutput;

public class CustomTypeNesting implements SQLData {
    public static final String NAME = "CUSTOM_TYPE_NESTING";
    private CustomType nested;

    public CustomTypeNesting() {}

    public CustomType getNested() {
        return this.nested;
    }
    public void setNested(CustomType nested) {
        this.nested = nested;
    }

    @Override
    public boolean equals(Object o) {
        if (! (o instanceof CustomTypeNesting)) {
            return false;
        }
        CustomTypeNesting other = (CustomTypeNesting) o;

        return
                (this.getNested() == null ? other.getNested() == null : this.getNested().equals(other.getNested()))
                ;
    }

    @Override
    public int hashCode() {
        return
                (this.getNested() == null ? 0 : this.getNested().hashCode())
                ;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "["
                + " nested=" + (this.getNested() == null ? "null" : this.getNested().toString())
                + " ]";
    }

    @Override
    public String getSQLTypeName() {
        return NAME;
    }

    @Override
    public void readSQL(SQLInput stream, String typeName) throws SQLException {
        this.setNested((CustomType) stream.readObject());
    }

    @Override
    public void writeSQL(SQLOutput stream) throws SQLException {
        stream.writeObject(this.getNested());
    }
}
