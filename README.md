[![Build Status](https://github.com/ericminio/java-oracle/actions/workflows/java7.yml/badge.svg)](https://github.com/ericminio/java-oracle/actions)
[![Build Status](https://github.com/ericminio/java-oracle/actions/workflows/java8.yml/badge.svg)](https://github.com/ericminio/java-oracle/actions)

```
docker-compose up java7 
docker-compose up java8 
```

# Usage

Assuming
- [migrations](service/migrations) have been run via `docker-compose up java8`
- active shell is bash inside java8 container via `docker-compose run java8 bash` 
- run `/usr/local/src/service/demos/run.sh`

```
Results :

Tests run: 43, Failures: 0, Errors: 0, Skipped: 0

[INFO]
[INFO] --- maven-jar-plugin:2.4:jar (default-jar) @ java-oracle ---
[INFO] Building jar: /usr/local/src/service/code/target/java-oracle-1.0.jar
[INFO]
[INFO] --- maven-assembly-plugin:3.3.0:single (default) @ java-oracle ---
[INFO] Building jar: /usr/local/src/service/code/target/java-oracle-1.0-jar-with-dependencies.jar
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  10.539 s
[INFO] Finished at: 2021-04-27T14:31:22Z
[INFO] ------------------------------------------------------------------------

Package specification:
PACKAGE function_with_parameter
AS

FUNCTION count_by_type(
value varchar2
) RETURN integer;

FUNCTION count_by_label(
value varchar2
) RETURN integer;

END function_with_parameter;
total 5
drwxrwxrwx    1 root     root          4096 Apr 27 14:31 .
drwxrwxrwx    1 root     root             0 Apr 26 20:20 ..
-rwxr-xr-x    1 root     root          1017 Apr 27 14:31 FunctionWithParameter.java
package company.name;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

public class FunctionWithParameter {

    private Connection connection;

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public int countByType(String value) throws SQLException {
        CallableStatement statement = connection.prepareCall("{? = call function_with_parameter.count_by_type(?)}");
        statement.registerOutParameter(1, Types.INTEGER);
        statement.setString(2, value);
        statement.execute();

        return statement.getInt(1);
    }

    public int countByLabel(String value) throws SQLException {
        CallableStatement statement = connection.prepareCall("{? = call function_with_parameter.count_by_label(?)}");
        statement.registerOutParameter(1, Types.INTEGER);
        statement.setString(2, value);
        statement.execute();

        return statement.getInt(1);
    }

}
```
