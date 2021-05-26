package ericminio.javaoracle.domain;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class GenerateImportsTest {

    GenerateImports generateImports;
    String template;

    @Before
    public void sut() {
        generateImports = new GenerateImports();
        template = "" +
                "import java.sql.CallableStatement;\n" +
                "import java.sql.Connection;\n" +
                "import java.sql.SQLException;\n" +
                "\n";
    }

    @Test
    public void preservesNonImportCode() {
        String code = template
                + "    anything but do not touch it   ;  \n"
                + "     because we need it as it is!  "
                ;
        String actual = generateImports.please(code);
        assertThat(actual, containsString("    anything but do not touch it   ;  \n     because we need it as it is!  "));
    }

    @Test
    public void forBigDecimal() {
        String code = template + "BigDecimal any = new BigDecimal()";
        assertThat(generateImports.please(code), containsString("import java.math.BigDecimal;\nimport java.sql.CallableStatement;"));
    }

    @Test
    public void forDate() {
        String code = template + "Date any = new Date()";
        assertThat(generateImports.please(code), containsString("import java.sql.SQLException;\nimport java.util.Date;"));
    }

    @Test
    public void forTypes() {
        String code = template + "statement.registerOutParameter(1, Types.NUMERIC);";
        assertThat(generateImports.please(code), containsString("import java.sql.SQLException;\nimport java.sql.Types;"));
    }

    @Test
    public void forClob() {
        String code = template + "public Clob getValue()";
        assertThat(generateImports.please(code), containsString("import java.sql.CallableStatement;\nimport java.sql.Clob;"));
    }

    @Test
    public void forCursor() {
        String code = template + "public ResultSet getValue()";
        assertThat(generateImports.please(code), containsString("import java.sql.ResultSet;\nimport java.sql.SQLException;"));
    }

}
