package ericminio.javaoracle.demos;

import java.math.BigDecimal;
import java.sql.SQLData;
import java.sql.SQLException;
import java.sql.SQLInput;
import java.sql.SQLOutput;
import java.util.Date;

public class CustomType implements SQLData {
    public static final String NAME = "CUSTOM_TYPE";
    private BigDecimal id;
    private String label;
    private Date creationDate;

    public CustomType() {}

    public BigDecimal getId() {
        return this.id;
    }
    public void setId(BigDecimal id) {
        this.id = id;
    }

    public String getLabel() {
        return this.label;
    }
    public void setLabel(String label) {
        this.label = label;
    }

    public Date getCreationDate() {
        return this.creationDate;
    }
    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public boolean equals(Object o) {
        if (! (o instanceof CustomType)) {
            return false;
        }
        CustomType other = (CustomType) o;

        return
                (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
                && (this.getLabel() == null ? other.getLabel() == null : this.getLabel().equals(other.getLabel()))
                && (this.getCreationDate() == null ? other.getCreationDate() == null : this.getCreationDate().equals(other.getCreationDate()))
                ;
    }

    @Override
    public int hashCode() {
        return
                (this.getId() == null ? 0 : this.getId().hashCode())
                + (this.getLabel() == null ? 0 : this.getLabel().hashCode())
                + (this.getCreationDate() == null ? 0 : this.getCreationDate().hashCode())
                ;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "["
                + " id=" + (this.getId() == null ? "null" : this.getId().toString())
                + ", label=" + (this.getLabel() == null ? "null" : this.getLabel().toString())
                + ", creationDate=" + (this.getCreationDate() == null ? "null" : this.getCreationDate().toString())
                + " ]";
    }

    @Override
    public String getSQLTypeName() {
        return NAME;
    }

    @Override
    public void readSQL(SQLInput stream, String typeName) throws SQLException {
        this.setId(stream.readBigDecimal());
        this.setLabel(stream.readString());
        this.setCreationDate(buildDateOrNull(stream.readTimestamp()));
    }

    private Date buildDateOrNull(java.sql.Timestamp timestamp) {
        if (timestamp == null) {
            return null;
        }
        else {
            return new Date(timestamp.getTime());
        }
    }

    @Override
    public void writeSQL(SQLOutput stream) throws SQLException {
        stream.writeBigDecimal(this.getId());
        stream.writeString(this.getLabel());
        stream.writeTimestamp(new java.sql.Timestamp(this.getCreationDate().getTime()));
    }
}
