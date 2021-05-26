package ericminio.javaoracle.domain;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

public class GeneratePackageCodeTest {

    GeneratePackageCode generatePackageCode;
    TypeMapperFactory typeMapperFactory;

    @Before
    public void types() {
        generatePackageCode = new GeneratePackageCode();
        typeMapperFactory = new TypeMapperFactory(Arrays.asList(
                Arrays.asList("create type any_type as object(value number)")
        ));
    }

    @Test
    public void disclosesPackageName() throws IOException {
        generatePackageCode.please(Arrays.asList(
                "PACKAGE EXPLORATION\n",
                "AS\n",
                "   FUNCTION get_event_count RETURN number;\n",
                "END any_package;"
        ), typeMapperFactory);
        assertThat(generatePackageCode.getPackageName(), equalTo("exploration"));
    }

    @Test
    public void generatesExpectedConstructor() throws IOException {
        String code = generatePackageCode.please(Arrays.asList(
                "PACKAGE any_package\n",
                "AS\n",
                "   FUNCTION any_function RETURN number;\n",
                "END any_package;"
        ), typeMapperFactory);
        assertThat(code, containsString("" +
                "    public AnyPackage(Connection connection) {\n" +
                "        this.connection = connection;\n" +
                "    }"
        ));
    }

    @Test
    public void generatesTypeMappingWhenFunctionReturnsCustomType() throws IOException {
        String code = generatePackageCode.please(Arrays.asList(
                "PACKAGE any_package\n",
                "AS\n",
                "   FUNCTION any_function RETURN any_type;\n",
                "END any_package;"
        ), typeMapperFactory);
        assertThat(code, containsString("" +
                "    public AnyPackage(Connection connection) throws SQLException {\n" +
                "        this.connection = connection;\n" +
                "        connection.getTypeMap().put(AnyType.NAME, AnyType.class);\n" +
                "    }"
        ));
    }

    @Test
    public void importsArePreservedWithoutBigDecimalByDefault() throws IOException {
        String code = generatePackageCode.please(Arrays.asList(
                "PACKAGE any_package\n",
                "AS\n",
                "   FUNCTION any_function RETURN any_type;\n",
                "END any_package;"
        ), typeMapperFactory);
        assertThat(code, not(containsString("import java.math.BigDecimal;\n")));
    }

    @Test
    public void importsArePreservedWithoutClobByDefault() throws IOException {
        String code = generatePackageCode.please(Arrays.asList(
                "PACKAGE any_package\n",
                "AS\n",
                "   FUNCTION any_function RETURN any_type;\n",
                "END any_package;"
        ), typeMapperFactory);
        assertThat(code, not(containsString("import java.sql.Clob;\n")));
    }

    @Test
    public void importsAreUpdatedIfBigDecimalIsUsedAsReturnType() throws IOException {
        String code = generatePackageCode.please(Arrays.asList(
                "PACKAGE any_package\n",
                "AS\n",
                "   FUNCTION any_function RETURN number;\n",
                "END any_package;"
        ), typeMapperFactory);
        assertThat(code, containsString("import java.math.BigDecimal;\n"));
    }

    @Test
    public void importsAreUpdatedIfClobIsUsedAsReturnType() throws IOException {
        String code = generatePackageCode.please(Arrays.asList(
                "PACKAGE any_package\n",
                "AS\n",
                "   FUNCTION any_function RETURN clob;\n",
                "END any_package;"
        ), typeMapperFactory);
        assertThat(code, containsString("import java.sql.Clob;\n"));
    }

    @Test
    public void importsAreUpdatedIfBigDecimalIsUsedAsParameter() throws IOException {
        String code = generatePackageCode.please(Arrays.asList(
                "PACKAGE any_package\n",
                "AS\n",
                "   FUNCTION any_function(param number) RETURN any_type;\n",
                "END any_package;"
        ), typeMapperFactory);
        assertThat(code, containsString("import java.math.BigDecimal;\n"));
    }

    @Test
    public void importsAreUpdatedIfClobIsUsedAsParameter() throws IOException {
        String code = generatePackageCode.please(Arrays.asList(
                "PACKAGE any_package\n",
                "AS\n",
                "   FUNCTION any_function(param clob) RETURN any_type;\n",
                "END any_package;"
        ), typeMapperFactory);
        assertThat(code, containsString("import java.sql.Clob;\n"));
    }

    @Test
    public void typeMappingIncludesRecordTypeWhenReturningArrayType() throws IOException {
        List<String> packageSpecification = Arrays.asList(
                "package any_package as",
                "   function any_function return array_type;",
                "end any_package;"
        );
        List<List<String>> typeSpecifications = Arrays.asList(
                Arrays.asList("create or replace type record_type as object(value number);"),
                Arrays.asList("create or replace type array_type as table of record_type;")
        );
        String code = new GeneratePackageCode().please(packageSpecification, new TypeMapperFactory(typeSpecifications));
        assertThat(code, containsString("" +
                "    public AnyPackage(Connection connection) throws SQLException {\n" +
                "        this.connection = connection;\n" +
                "        connection.getTypeMap().put(RecordType.NAME, RecordType.class);\n" +
                "    }"
        ));
    }

    @Test
    public void typeMappingIncludesNestedType() throws IOException {
        List<String> packageSpecification = Arrays.asList(
                "package any_package as",
                "   function any_function return any_type_nesting;",
                "end any_package;"
        );
        List<List<String>> typeSpecifications = Arrays.asList(
                Arrays.asList("create or replace type any_type as object(value number);"),
                Arrays.asList("create or replace type any_type_nesting as object(nested any_type);")
        );
        String code = new GeneratePackageCode().please(packageSpecification, new TypeMapperFactory(typeSpecifications));
        assertThat(code, containsString("" +
                "    public AnyPackage(Connection connection) throws SQLException {\n" +
                "        this.connection = connection;\n" +
                "        connection.getTypeMap().put(AnyTypeNesting.NAME, AnyTypeNesting.class);\n" +
                "        connection.getTypeMap().put(AnyType.NAME, AnyType.class);\n" +
                "    }"
        ));
    }
}
