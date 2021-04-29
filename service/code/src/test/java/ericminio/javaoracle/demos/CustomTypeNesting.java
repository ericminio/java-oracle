package ericminio.javaoracle.demos;

import java.sql.SQLData;
import java.sql.SQLException;
import java.sql.SQLInput;
import java.sql.SQLOutput;

public class CustomTypeNesting implements SQLData {
    public static final String NAME = "CUSTOM_TYPE_NESTING";
    private CustomTypeNested value;

    public CustomTypeNesting() {}

    public CustomTypeNested getValue() {
        return this.value;
    }
    public void setValue(CustomTypeNested value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (! (o instanceof CustomTypeNesting)) {
            return false;
        }
        CustomTypeNesting other = (CustomTypeNesting) o;

        return
                this.getValue().equals(other.getValue())
                ;
    }

    @Override
    public int hashCode() {
        return
                this.getValue().hashCode()
                ;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "["
                + " value=" + (this.getValue() == null ? "null" : this.getValue().toString())
                + " ]";
    }

    @Override
    public String getSQLTypeName() {
        return NAME;
    }

    @Override
    public void readSQL(SQLInput stream, String typeName) throws SQLException {
        this.setValue((CustomTypeNested) stream.readObject());
    }

    @Override
    public void writeSQL(SQLOutput stream) throws SQLException {
        this.getValue().writeSQL(stream);
    }
}
