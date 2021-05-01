[![Build Status](https://github.com/ericminio/java-oracle/actions/workflows/java7.yml/badge.svg)](https://github.com/ericminio/java-oracle/actions)
[![Build Status](https://github.com/ericminio/java-oracle/actions/workflows/java8.yml/badge.svg)](https://github.com/ericminio/java-oracle/actions)
[![Build Status](https://github.com/ericminio/java-oracle/actions/workflows/java11.yml/badge.svg)](https://github.com/ericminio/java-oracle/actions)

# Run the tests
```
docker-compose up java7 
docker-compose up java8 
docker-compose up java11 
```

# Usage

Study [service/demos/run.sh](service/demos/run.sh).

One way to see it running:
- `docker-compose run java8 bash` 
- `/usr/local/src/service/demos/run.sh`

```
drwxrwxrwx    1 root     root          4096 May  1 00:02 .
drwxrwxrwx    1 root     root             0 Apr 28 16:11 ..
-rwxr-xr-x    1 root     root          2448 May  1 00:02 Example.java
-rwxr-xr-x    1 root     root          1568 May  1 00:02 ExampleTypesOne.java
-rwxr-xr-x    1 root     root          1608 May  1 00:02 ExampleTypesThree.java
-rwxr-xr-x    1 root     root          1519 May  1 00:02 ExampleTypesTwo.java
-rwxr-xr-x    1 root     root          1304 May  1 00:02 run.sh

TEXT
--------------------------------------------------------------------------------
type example_types_one as object(value number(15,4));
type example_types_three as object(value date);
type example_types_two as object(value varchar2(20));

package company.name;

import java.math.BigDecimal;
import java.sql.SQLData;
import java.sql.SQLException;
import java.sql.SQLInput;
import java.sql.SQLOutput;

public class ExampleTypesOne implements SQLData {
    public static final String NAME = "EXAMPLE_TYPES_ONE";
    private BigDecimal value;

    public ExampleTypesOne() {}

    public BigDecimal getValue() {
        return this.value;
    }
    public void setValue(BigDecimal value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (! (o instanceof ExampleTypesOne)) {
            return false;
        }
        ExampleTypesOne other = (ExampleTypesOne) o;

        return
                (this.getValue() == null ? other.getValue() == null : this.getValue().equals(other.getValue()))
                ;
    }

    @Override
    public int hashCode() {
        return
                (this.getValue() == null ? 0 : this.getValue().hashCode())
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
        this.setValue(stream.readBigDecimal());
    }

    @Override
    public void writeSQL(SQLOutput stream) throws SQLException {
        stream.writeBigDecimal(this.getValue());
    }
}
package company.name;

import java.sql.SQLData;
import java.sql.SQLException;
import java.sql.SQLInput;
import java.sql.SQLOutput;

public class ExampleTypesTwo implements SQLData {
    public static final String NAME = "EXAMPLE_TYPES_TWO";
    private String value;

    public ExampleTypesTwo() {}

    public String getValue() {
        return this.value;
    }
    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (! (o instanceof ExampleTypesTwo)) {
            return false;
        }
        ExampleTypesTwo other = (ExampleTypesTwo) o;

        return
                (this.getValue() == null ? other.getValue() == null : this.getValue().equals(other.getValue()))
                ;
    }

    @Override
    public int hashCode() {
        return
                (this.getValue() == null ? 0 : this.getValue().hashCode())
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
        this.setValue(stream.readString());
    }

    @Override
    public void writeSQL(SQLOutput stream) throws SQLException {
        stream.writeString(this.getValue());
    }
}
package company.name;

import java.sql.SQLData;
import java.sql.SQLException;
import java.sql.SQLInput;
import java.sql.SQLOutput;
import java.util.Date;

public class ExampleTypesThree implements SQLData {
    public static final String NAME = "EXAMPLE_TYPES_THREE";
    private Date value;

    public ExampleTypesThree() {}

    public Date getValue() {
        return this.value;
    }
    public void setValue(Date value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (! (o instanceof ExampleTypesThree)) {
            return false;
        }
        ExampleTypesThree other = (ExampleTypesThree) o;

        return
                (this.getValue() == null ? other.getValue() == null : this.getValue().equals(other.getValue()))
                ;
    }

    @Override
    public int hashCode() {
        return
                (this.getValue() == null ? 0 : this.getValue().hashCode())
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
        this.setValue(new Date(stream.readTimestamp().getTime()));
    }

    @Override
    public void writeSQL(SQLOutput stream) throws SQLException {
        stream.writeTimestamp(new java.sql.Timestamp(this.getValue().getTime()));
    }
}

TEXT
--------------------------------------------------------------------------------
PACKAGE example
AS
    FUNCTION hello(value1 varchar2) RETURN number;
    FUNCTION world(value2 number) RETURN varchar2;
    FUNCTION given(value3 date) RETURN date;
    FUNCTION when(value4 example_types_one) RETURN example_types_one;
    FUNCTION then(value5 example_types_one, value6 example_types_two) RETURN exa
mple_types_two;

END example

8 rows selected.

package company.name;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class Example {

    private Connection connection;

    public Example(Connection connection) throws SQLException {
        this.connection = connection;
        connection.getTypeMap().put(ExampleTypesOne.NAME, ExampleTypesOne.class);
        connection.getTypeMap().put(ExampleTypesTwo.NAME, ExampleTypesTwo.class);
    }

    public BigDecimal hello(String value1) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("select example.hello(?) from dual");
        statement.setString(1, value1);
        ResultSet resultSet = statement.executeQuery();
        resultSet.next();

        return (BigDecimal) resultSet.getObject(1);
    }

    public String world(BigDecimal value2) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("select example.world(?) from dual");
        statement.setBigDecimal(1, value2);
        ResultSet resultSet = statement.executeQuery();
        resultSet.next();

        return (String) resultSet.getObject(1);
    }

    public Date given(Date value3) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("select example.given(?) from dual");
        statement.setTimestamp(1, new java.sql.Timestamp(value3.getTime()));
        ResultSet resultSet = statement.executeQuery();
        resultSet.next();

        return new Date( ((java.sql.Timestamp) resultSet.getObject(1)).getTime() );
    }

    public ExampleTypesOne when(ExampleTypesOne value4) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("select example.when(?) from dual");
        statement.setObject(1, value4);
        ResultSet resultSet = statement.executeQuery();
        resultSet.next();

        return (ExampleTypesOne) resultSet.getObject(1);
    }

    public ExampleTypesTwo then(ExampleTypesOne value5, ExampleTypesTwo value6) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("select example.then(?, ?) from dual");
        statement.setObject(1, value5);
        statement.setObject(2, value6);
        ResultSet resultSet = statement.executeQuery();
        resultSet.next();

        return (ExampleTypesTwo) resultSet.getObject(1);
    }

}
```
