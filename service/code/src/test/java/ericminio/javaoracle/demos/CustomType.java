package ericminio.javaoracle.demos;

import java.sql.SQLData;
import java.sql.SQLException;
import java.sql.SQLInput;
import java.sql.SQLOutput;

public class CustomType implements SQLData {
    public static final String NAME = "CUSTOM_TYPE";
    private Integer value;

    public CustomType() {}

    public Integer getValue() {
        return this.value;
    }
    public void setValue(Integer value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (! (o instanceof CustomType)) {
            return false;
        }
        CustomType other = (CustomType) o;

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
                + " value=" + ( this.getValue() == null ? "null" : this.getValue().toString())
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
