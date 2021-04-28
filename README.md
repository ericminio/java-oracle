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
TEXT
--------------------------------------------------------------------------------
PACKAGE example
AS

    FUNCTION hello(
        value1 varchar2
    ) RETURN integer;

    FUNCTION world(
        value2 integer
    ) RETURN varchar2;


TEXT
--------------------------------------------------------------------------------
END example

12 rows selected.

total 2
drwxrwxrwx    1 root     root             0 Apr 28 23:06 .
drwxrwxrwx    1 root     root             0 Apr 28 16:11 ..
-rwxr-xr-x    1 root     root           958 Apr 28 23:06 Example.java
-rwxr-xr-x    1 root     root           781 Apr 28 22:42 run.sh
package company.name;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Example {

    private Connection connection;

    public Example(Connection connection) {
        this.connection = connection;
    }

    public Integer hello(String value1) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("select example.hello(?) from dual");
        statement.setString(1, value1);
        ResultSet resultSet = statement.executeQuery();
        resultSet.next();

        return resultSet.getInt(1);
    }

    public String world(Integer value2) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("select example.world(?) from dual");
        statement.setInt(1, value2);
        ResultSet resultSet = statement.executeQuery();
        resultSet.next();

        return resultSet.getString(1);
    }

}
```
