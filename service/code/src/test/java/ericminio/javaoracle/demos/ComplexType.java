package ericminio.javaoracle.demos;

import java.sql.*;

public class ComplexType implements SQLData {
    public static final String NAME = "COMPLEX_TYPE";
    private CustomType value;
    private ArrayOfCustomType collection;

    public ComplexType() {}

    public CustomType getValue() {
        return this.value;
    }
    public void setValue(CustomType value) {
        this.value = value;
    }

    public ArrayOfCustomType getCollection() {
        return this.collection;
    }
    public void setCollection(ArrayOfCustomType collection) {
        this.collection = collection;
    }

    @Override
    public boolean equals(Object o) {
        if (! (o instanceof ComplexType)) {
            return false;
        }
        ComplexType other = (ComplexType) o;

        return
                (this.getValue() == null ? other.getValue() == null : this.getValue().equals(other.getValue()))
                && (this.getCollection() == null ? other.getCollection() == null : this.getCollection().equals(other.getCollection()))
                ;
    }

    @Override
    public int hashCode() {
        return
                (this.getValue() == null ? 0 : this.getValue().hashCode())
                + (this.getCollection() == null ? 0 : this.getCollection().hashCode())
                ;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "["
                + " value=" + (this.getValue() == null ? "null" : this.getValue().toString())
                + " collection=" + (this.getCollection() == null ? "null" : this.getCollection().toString())
                + " ]";
    }

    @Override
    public String getSQLTypeName() {
        return NAME;
    }

    @Override
    public void readSQL(SQLInput stream, String typeName) throws SQLException {
        this.setValue((CustomType) stream.readObject());
        this.setCollection(ArrayOfCustomType.with((Object[]) stream.readArray().getArray()));
    }

    @Override
    public void writeSQL(SQLOutput stream) throws SQLException {
        stream.writeObject(this.getValue());
    }
}
