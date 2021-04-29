package ericminio.javaoracle.demos;

import java.sql.SQLData;
import java.sql.SQLException;
import java.sql.SQLInput;
import java.sql.SQLOutput;

public class CustomTypeNested implements SQLData {
    public static final String NAME = "CUSTOM_TYPE_NESTED";
    private Integer value;

    public CustomTypeNested() {}

    public Integer getValue() {
        return this.value;
    }
    public void setValue(Integer value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (! (o instanceof CustomTypeNested)) {
            return false;
        }
        CustomTypeNested other = (CustomTypeNested) o;

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
        this.setValue(stream.readInt());
    }

    @Override
    public void writeSQL(SQLOutput stream) throws SQLException {
        stream.writeInt(this.getValue());
    }
}
