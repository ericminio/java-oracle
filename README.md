[![Build Status](https://github.com/ericminio/java-oracle/actions/workflows/java7.yml/badge.svg)](https://github.com/ericminio/java-oracle/actions)
[![Build Status](https://github.com/ericminio/java-oracle/actions/workflows/java8.yml/badge.svg)](https://github.com/ericminio/java-oracle/actions)

```
docker-compose up java7 
docker-compose up java8 
```

# Usage

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
drwxrwxrwx    1 root     root             0 Apr 27 18:11 .
drwxrwxrwx    1 root     root             0 Apr 26 20:19 ..
-rwxr-xr-x    1 root     root           945 Apr 27 18:11 Example.java
-rwxr-xr-x    1 root     root           774 Apr 27 18:11 run.sh
package company.name;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

public class Example {

    private Connection connection;

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public int hello(String value1) throws SQLException {
        CallableStatement statement = connection.prepareCall("{? = call example.hello(?)}");
        statement.registerOutParameter(1, Types.INTEGER);
        statement.setString(2, value1);
        statement.execute();

        return statement.getInt(1);
    }

    public String world(int value2) throws SQLException {
        CallableStatement statement = connection.prepareCall("{? = call example.world(?)}");
        statement.registerOutParameter(1, Types.VARCHAR);
        statement.setInt(2, value2);
        statement.execute();

        return statement.getString(1);
    }

}
```
