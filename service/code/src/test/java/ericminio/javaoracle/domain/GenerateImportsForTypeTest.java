package ericminio.javaoracle.domain;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;

public class GenerateImportsForTypeTest {

    GenerateImports generateImports;
    String template;

    @Before
    public void sut() {
        generateImports = new GenerateImports();
        template = "" +
                "import java.sql.SQLData;\n" +
                "import java.sql.SQLException;\n" +
                "import java.sql.SQLInput;\n" +
                "import java.sql.SQLOutput;\n" +
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
        String code = template + "public BigDecimal getValue()";
        assertThat(generateImports.please(code), containsString("import java.math.BigDecimal;\nimport java.sql.SQLData;"));
    }

    @Test
    public void forDate() {
        String code = template + "public Date getValue()";
        assertThat(generateImports.please(code), containsString("import java.sql.SQLOutput;\nimport java.util.Date;"));
    }

    @Test
    public void forClob() {
        String code = template + "public Clob getValue()";
        assertThat(generateImports.please(code), containsString("import java.sql.Clob;\nimport java.sql.SQLData;"));
    }

    @Test
    public void forClobAndBigDecimal() {
        String code = template + "private Clob field1; \n private BigDecimal field2;";
        assertThat(generateImports.please(code), containsString("import java.math.BigDecimal;\nimport java.sql.Clob;\nimport java.sql.SQLData;"));
    }

}
