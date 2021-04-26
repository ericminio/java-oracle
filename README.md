[![Build Status](https://github.com/ericminio/java-oracle/actions/workflows/java7.yml/badge.svg)](https://github.com/ericminio/java-oracle/actions)
[![Build Status](https://github.com/ericminio/java-oracle/actions/workflows/java8.yml/badge.svg)](https://github.com/ericminio/java-oracle/actions)

```
docker-compose up java7 
docker-compose up java8 
```

# Usage

Assuming
- oracle is started via `docker-compose up -d oracle`
- [migrations](service/migrations) have been run via `docker-compose up java8`
- active shell is bash inside java8 container via `docker-compose run java8 bash` 
- run `/usr/local/src/service/demos/run.sh`

```
Results :

Tests run: 17, Failures: 0, Errors: 0, Skipped: 0

[INFO]
[INFO] --- maven-jar-plugin:2.4:jar (default-jar) @ java-oracle ---
[INFO] Building jar: /usr/local/src/service/code/target/java-oracle-1.0.jar
[INFO]
[INFO] --- maven-assembly-plugin:3.3.0:single (default) @ java-oracle ---
[INFO] Building jar: /usr/local/src/service/code/target/java-oracle-1.0-jar-with-dependencies.jar
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time: 7.858 s
[INFO] Finished at: 2021-04-26T21:41:05Z
[INFO] ------------------------------------------------------------------------

Package specification:
PACKAGE exploration
AS

FUNCTION get_event_count RETURN INTEGER;

END exploration;
total 1
drwxrwxrwx    1 root     root             0 Apr 26 21:12 .
drwxrwxrwx    1 root     root             0 Apr 26 20:20 ..
-rwxr-xr-x    1 root     root           601 Apr 26 21:41 Exploration.java
package company.name;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

public class Exploration {

    private Connection connection;

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public int getEventCount() throws SQLException {
        CallableStatement statement = connection.prepareCall("{? = call exploration.get_event_count()}");
        statement.registerOutParameter(1, Types.INTEGER);
        statement.executeUpdate();

        return statement.getInt(1);
    }

}
```
