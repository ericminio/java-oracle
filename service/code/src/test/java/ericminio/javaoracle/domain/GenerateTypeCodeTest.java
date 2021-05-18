package ericminio.javaoracle.domain;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

public class GenerateTypeCodeTest {

    private String code;

    @Before
    public void generateCode() throws IOException {
        List<String> sut = Arrays.asList(
                "type custom_type as object\n",
                "(\n",
                "   field1 number,\n",
                "   field2 varchar2(10),\n",
                "   field3 array_type,\n",
                "   field4 clob\n",
                ")"
        );
        List<List<String>> typeSpecifications = Arrays.asList(
                Arrays.asList(
                        "create or replace type record_type as object(\n",
                        "   value number\n",
                        ");\n"),
                Arrays.asList("create or replace type array_type as table of record_type;"),
                sut
        );
        code = new GenerateTypeCode().please(sut, new TypeMapperFactory(typeSpecifications));
    }

    @Test
    public void importsIncludeBigDecimal() {
        assertThat(code, containsString("import java.math.BigDecimal;\n"));
    }

    @Test
    public void importsIncludeClob() {
        assertThat(code, containsString("import java.sql.Clob;\nimport java.sql.SQLData;\n"));
    }

    @Test
    public void importsDoNotAlwaysNeedBigDecimal() throws IOException {
        List<String> typeSpecification = Arrays.asList(
                "type custom_type as object\n",
                "(\n",
                "   field varchar2(10)\n",
                ")"
        );
        GenerateTypeCode generateTypeCode = new GenerateTypeCode();
        code = generateTypeCode.please(typeSpecification, new TypeMapperFactory());
        assertThat(code, not(containsString("import java.math.BigDecimal;")));
    }

    @Test
    public void importsDoNotAlwaysNeedClob() throws IOException {
        List<String> typeSpecification = Arrays.asList(
                "type custom_type as object\n",
                "(\n",
                "   field varchar2(10)\n",
                ")"
        );
        GenerateTypeCode generateTypeCode = new GenerateTypeCode();
        code = generateTypeCode.please(typeSpecification, new TypeMapperFactory());
        assertThat(code, not(containsString("import java.sql.Clob;")));
    }

    @Test
    public void className() {
        assertThat(code, containsString("public class CustomType implements SQLData {"));
    }

    @Test
    public void typeNameStaticField() {
        assertThat(code, containsString("public static final String NAME = \"CUSTOM_TYPE\";"));
    }

    @Test
    public void emptyConstructor() {
        assertThat(code, containsString("public CustomType() {}"));
    }

    @Test
    public void numberField() {
        assertThat(code, containsString("private BigDecimal field1;"));
    }

    @Test
    public void stringField() {
        assertThat(code, containsString("private String field2;"));
    }

    @Test
    public void arrayField() {
        assertThat(code, containsString("private ArrayType field3;"));
    }

    @Test
    public void clobField() {
        assertThat(code, containsString("private Clob field4;"));
    }

    @Test
    public void accessors() {
        assertThat(code, containsString(""
                + "    public BigDecimal getField1() {\n"
                + "        return this.field1;\n"
                + "    }\n"
                + "    public void setField1(BigDecimal field1) {\n"
                + "        this.field1 = field1;\n"
                + "    }\n"
                + "\n"
                + "    public String getField2() {\n"
                + "        return this.field2;\n"
                + "    }\n"
                + "    public void setField2(String field2) {\n"
                + "        this.field2 = field2;\n"
                + "    }\n"
                + "\n"
                + "    public ArrayType getField3() {\n"
                + "        return this.field3;\n"
                + "    }\n"
                + "    public void setField3(ArrayType field3) {\n"
                + "        this.field3 = field3;\n"
                + "    }\n"
                + "\n"
                + "    public Clob getField4() {\n"
                + "        return this.field4;\n"
                + "    }\n"
                + "    public void setField4(Clob field4) {\n"
                + "        this.field4 = field4;\n"
                + "    }\n"
        ));
    }

    @Test
    public void equalsMethod() {
        assertThat(code, containsString("" +
                "    @Override\n" +
                "    public boolean equals(Object o) {\n" +
                "        if (! (o instanceof CustomType)) {\n" +
                "            return false;\n" +
                "        }\n" +
                "        CustomType other = (CustomType) o;\n" +
                "\n" +
                "        return\n" +
                "                (this.getField1() == null ? other.getField1() == null : this.getField1().equals(other.getField1()))\n" +
                "                && (this.getField2() == null ? other.getField2() == null : this.getField2().equals(other.getField2()))\n" +
                "                && (this.getField3() == null ? other.getField3() == null : this.getField3().equals(other.getField3()))\n" +
                "                && (this.getField4() == null ? other.getField4() == null : this.getField4().equals(other.getField4()))\n" +
                "                ;\n" +
                "    }"
        ));
    }

    @Test
    public void hashcodeMethod() {
        assertThat(code, containsString("" +
                "    @Override\n" +
                "    public int hashCode() {\n" +
                "        return\n" +
                "                (this.getField1() == null ? 0 : this.getField1().hashCode())\n" +
                "                + (this.getField2() == null ? 0 : this.getField2().hashCode())\n" +
                "                + (this.getField3() == null ? 0 : this.getField3().hashCode())\n" +
                "                + (this.getField4() == null ? 0 : this.getField4().hashCode())\n" +
                "                ;\n" +
                "    }"
        ));
    }

    @Test
    public void toStringMethod() {
        assertThat(code, containsString("" +
                "    @Override\n" +
                "    public String toString() {\n" +
                "        return this.getClass().getSimpleName() + \"[\"\n" +
                "                + \" field1=\" + (this.getField1() == null ? \"null\" : this.getField1().toString())\n" +
                "                + \", field2=\" + (this.getField2() == null ? \"null\" : this.getField2().toString())\n" +
                "                + \", field3=\" + (this.getField3() == null ? \"null\" : this.getField3().toString())\n" +
                "                + \", field4=\" + (this.getField4() == null ? \"null\" : this.getField4().toString())\n" +
                "                + \" ]\";\n" +
                "    }"
        ));
    }

    @Test
    public void readSQLMethod() {
        assertThat(code, containsString("" +
                "    @Override\n" +
                "    public void readSQL(SQLInput stream, String typeName) throws SQLException {\n" +
                "        this.setField1(stream.readBigDecimal());\n" +
                "        this.setField2(stream.readString());\n" +
                "        this.setField3(ArrayType.with((Object[]) stream.readArray().getArray()));\n" +
                "        this.setField4(stream.readClob());\n" +
                "    }"
        ));
    }

    @Test
    public void writeSQLMethod() {
        assertThat(code, containsString("" +
                "    @Override\n" +
                "    public void writeSQL(SQLOutput stream) throws SQLException {\n" +
                "        stream.writeBigDecimal(this.getField1());\n" +
                "        stream.writeString(this.getField2());\n" +
                "        // ignore field3\n" +
                "        stream.writeClob(this.getField4());\n" +
                "    }"
        ));
    }

    @Test
    public void ignoresCommentsInDefinition() throws IOException {
        List<String> typeSpecification = Arrays.asList(
                "type custom_type as object(\n",
                "   \n",
                "   -- this comment should be ignored\n\n",
                "   \n",
                "   field1 number, -- that comment too\n",
                "   field2 varchar2(10),\n",
                "   field3 array_type\n",
                ")"
        );
        List<List<String>> typeSpecifications = Arrays.asList(
                Arrays.asList(
                        "create or replace type record_type as object(\n",
                        "   value number\n",
                        ");\n"),
                Arrays.asList("create or replace type array_type as table of record_type;")
        );
        TypeMapperFactory typeMapperFactory = new TypeMapperFactory(typeSpecifications);
        GenerateTypeCode generateTypeCode = new GenerateTypeCode();
        code = generateTypeCode.please(typeSpecification, typeMapperFactory);

        assertThat(code, containsString("public class CustomType implements SQLData {"));
    }
}
