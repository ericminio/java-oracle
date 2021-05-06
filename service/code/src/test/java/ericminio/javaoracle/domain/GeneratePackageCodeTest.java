package ericminio.javaoracle.domain;

import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

public class GeneratePackageCodeTest {

    @Test
    public void disclosesPackageName() throws IOException {
        GeneratePackageCode generatePackageCode = new GeneratePackageCode();
        generatePackageCode.please(Arrays.asList(
                "PACKAGE EXPLORATION\n",
                "AS\n",
                "   FUNCTION get_event_count RETURN number;\n",
                "END EXPLORATION;"
        ));
        assertThat(generatePackageCode.getPackageName(), equalTo("exploration"));
    }

    @Test
    public void generatesExpectedConstructor() throws IOException {
        GeneratePackageCode generatePackageCode = new GeneratePackageCode();
        String code = generatePackageCode.please(Arrays.asList(
                "PACKAGE any_package\n",
                "AS\n",
                "   FUNCTION any_function RETURN number;\n",
                "END EXPLORATION;"
        ));
        assertThat(code, containsString("" +
                "    public AnyPackage(Connection connection) {\n" +
                "        this.connection = connection;\n" +
                "    }"
        ));
    }

    @Test
    public void generatesTypeMappingWhenFunctionReturnsCustomType() throws IOException {
        GeneratePackageCode generatePackageCode = new GeneratePackageCode();
        String code = generatePackageCode.please(Arrays.asList(
                "PACKAGE any_package\n",
                "AS\n",
                "   FUNCTION any_function RETURN any_type;\n",
                "END EXPLORATION;"
        ));
        assertThat(code, containsString("" +
                "    public AnyPackage(Connection connection) throws SQLException {\n" +
                "        this.connection = connection;\n" +
                "        connection.getTypeMap().put(AnyType.NAME, AnyType.class);\n" +
                "    }"
        ));
    }

    @Test
    public void importsArePreservedByDefault() throws IOException {
        GeneratePackageCode generatePackageCode = new GeneratePackageCode();
        String code = generatePackageCode.please(Arrays.asList(
                "PACKAGE any_package\n",
                "AS\n",
                "   FUNCTION any_function RETURN any_type;\n",
                "END EXPLORATION;"
        ));
        assertThat(code, not(containsString("import java.math.BigDecimal;\n")));
    }

    @Test
    public void importsAreUpdatedIfBigDecimalIsUsedAsReturnType() throws IOException {
        GeneratePackageCode generatePackageCode = new GeneratePackageCode();
        String code = generatePackageCode.please(Arrays.asList(
                "PACKAGE any_package\n",
                "AS\n",
                "   FUNCTION any_function RETURN number;\n",
                "END EXPLORATION;"
        ));
        assertThat(code, containsString("import java.math.BigDecimal;\n"));
    }

    @Test
    public void importsAreUpdatedIfBigDecimalIsUsedAsParameter() throws IOException {
        GeneratePackageCode generatePackageCode = new GeneratePackageCode();
        String code = generatePackageCode.please(Arrays.asList(
                "PACKAGE any_package\n",
                "AS\n",
                "   FUNCTION any_function(param number) RETURN any_type;\n",
                "END EXPLORATION;"
        ));
        assertThat(code, containsString("import java.math.BigDecimal;\n"));
    }

    @Test
    public void typeMappingIncludesRecordTypeWhenReturningArrayType() throws IOException {
        GeneratePackageCode generatePackageCode = new GeneratePackageCode();
        List<String> packageSpecification = Arrays.asList(
                "PACKAGE any_package\n",
                "AS\n",
                "   FUNCTION any_function RETURN array_type;\n",
                "END EXPLORATION;"
        );
        List<List<String>> typeSpecifications = Arrays.asList(
                Arrays.asList(
                        "create or replace type record_type as object(\n",
                        "   value number\n",
                        ");\n"),
                Arrays.asList("create or replace type array_type as table of record_type;")
        );
        TypeMapperFactory typeMapperFactory = new TypeMapperFactory(typeSpecifications);
        String code = generatePackageCode.please(packageSpecification, typeMapperFactory);
        assertThat(code, containsString("" +
                "    public AnyPackage(Connection connection) throws SQLException {\n" +
                "        this.connection = connection;\n" +
                "        connection.getTypeMap().put(RecordType.NAME, RecordType.class);\n" +
                "    }"
        ));
    }
}