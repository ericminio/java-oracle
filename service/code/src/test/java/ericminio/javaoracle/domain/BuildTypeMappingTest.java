package ericminio.javaoracle.domain;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class BuildTypeMappingTest {

    private BuildTypeMapping buildTypeMapping;
    
    @Before
    public void initTypes() {
        List<List<String>> typeSpecifications = Arrays.asList(
                Arrays.asList("create or replace type record_type as object (value number);"),
                Arrays.asList("create or replace type any_table_type as table of record_type;"),
                Arrays.asList("create or replace type table_type_of_varchar as table of varchar2(15);"),
                Arrays.asList("create or replace type any_type_nesting as object (nested any_type_nested);"),
                Arrays.asList("create or replace type any_type_nested as object (id number);"),
                Arrays.asList("create or replace type any_type_nesting_twice as object (one any_type_nested, two any_type_nested);"),
                Arrays.asList("create or replace type any_type as object (value number);"),
                Arrays.asList("create or replace type another_type as object (value number);")
                );
        TypeMapperFactory typeMapperFactory = new TypeMapperFactory(typeSpecifications);
        buildTypeMapping = new BuildTypeMapping(typeMapperFactory);
    }

    @Test
    public void emptyByDefault() {
        assertThat(buildTypeMapping.please(new ArrayList<String>()), equalTo(""));
    }

    @Test
    public void notNeededForVarchar() {
        assertThat(buildTypeMapping.please(Arrays.asList("varchar2")), equalTo(""));
    }

    @Test
    public void notNeededForInteger() {
        assertThat(buildTypeMapping.please(Arrays.asList("integer")), equalTo(""));
    }

    @Test
    public void notNeededForNumber() {
        assertThat(buildTypeMapping.please(Arrays.asList("number")), equalTo(""));
    }

    @Test
    public void notNeededForDate() {
        assertThat(buildTypeMapping.please(Arrays.asList("date")), equalTo(""));
    }

    @Test
    public void neededForCustomTypes() {
        assertThat(buildTypeMapping.please(Arrays.asList("any_type", "another_type")), equalTo("" +
                "        connection.getTypeMap().put(AnyType.NAME, AnyType.class);\n" +
                "        connection.getTypeMap().put(AnotherType.NAME, AnotherType.class);\n"
        ));
    }

    @Test
    public void declaresRecordTypeForArrayType() {
        assertThat(buildTypeMapping.please(Arrays.asList("any_table_type")), equalTo("" +
                "        connection.getTypeMap().put(RecordType.NAME, RecordType.class);\n"
        ));
    }

    @Test
    public void declaresRecordTypeForArrayTypeExceptForNativeTypes() {
        assertThat(buildTypeMapping.please(Arrays.asList("table_type_of_varchar")), equalTo(""));
    }

    @Test
    public void declaresNestedType() {
        assertThat(buildTypeMapping.please(Arrays.asList("any_type_nesting")), equalTo("" +
                "        connection.getTypeMap().put(AnyTypeNesting.NAME, AnyTypeNesting.class);\n" +
                "        connection.getTypeMap().put(AnyTypeNested.NAME, AnyTypeNested.class);\n"
        ));
    }

    @Test
    public void declaresNestedTypeOnlyOnce() {
        assertThat(buildTypeMapping.please(Arrays.asList("any_type_nesting_twice")), equalTo("" +
                "        connection.getTypeMap().put(AnyTypeNestingTwice.NAME, AnyTypeNestingTwice.class);\n" +
                "        connection.getTypeMap().put(AnyTypeNested.NAME, AnyTypeNested.class);\n"
        ));
    }
}
