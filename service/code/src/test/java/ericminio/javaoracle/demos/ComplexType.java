package ericminio.javaoracle.demos;

import java.sql.SQLData;
import java.sql.SQLException;
import java.sql.SQLInput;
import java.sql.SQLOutput;

public class ComplexType implements SQLData {
    public static final String NAME = "COMPLEX_TYPE";
    private CustomType value;

    public ComplexType() {}

    public CustomType getValue() {
        return this.value;
    }
    public void setValue(CustomType value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (! (o instanceof ComplexType)) {
            return false;
        }
        ComplexType other = (ComplexType) o;

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
        this.setValue((CustomType) stream.readObject());
    }

    @Override
    public void writeSQL(SQLOutput stream) throws SQLException {
        stream.writeObject(this.getValue());
    }
}
