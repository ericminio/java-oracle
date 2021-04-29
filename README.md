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
drwxrwxrwx    1 root     root             0 Apr 29 23:42 .
drwxrwxrwx    1 root     root             0 Apr 28 16:11 ..
-rwxr-xr-x    1 root     root          1414 Apr 29 23:42 Example.java
-rwxr-xr-x    1 root     root          1455 Apr 29 23:42 ExampleType.java
-rwxr-xr-x    1 root     root          1168 Apr 29 23:42 run.sh

TEXT
--------------------------------------------------------------------------------
type example_type as object(value number);

package company.name;

import java.math.BigDecimal;
import java.sql.SQLData;
import java.sql.SQLException;
import java.sql.SQLInput;
import java.sql.SQLOutput;

public class ExampleType implements SQLData {
    public static final String NAME = "EXAMPLE_TYPE";
    private BigDecimal value;

    public ExampleType() {}

    public BigDecimal getValue() {
        return this.value;
    }
    public void setValue(BigDecimal value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (! (o instanceof ExampleType)) {
            return false;
        }
        ExampleType other = (ExampleType) o;

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
        this.setValue(stream.readBigDecimal());
    }

    @Override
    public void writeSQL(SQLOutput stream) throws SQLException {
        stream.writeBigDecimal(this.getValue());
    }
}
TEXT
--------------------------------------------------------------------------------
PACKAGE example
AS
    FUNCTION hello(value1 varchar2) RETURN number;
    FUNCTION world(value2 number) RETURN varchar2;
    FUNCTION yop RETURN example_type;
END example

6 rows selected.

package company.name;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Example {

    private Connection connection;

    public Example(Connection connection) throws SQLException {
        this.connection = connection;
        connection.getTypeMap().put(ExampleType.NAME, ExampleType.class);
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

        return (String) resultSet.getString(1);
    }

    public ExampleType yop() throws SQLException {
        PreparedStatement statement = connection.prepareStatement("select example.yop() from dual");
        ResultSet resultSet = statement.executeQuery();
        resultSet.next();

        return (ExampleType) resultSet.getObject(1);
    }

}
```
