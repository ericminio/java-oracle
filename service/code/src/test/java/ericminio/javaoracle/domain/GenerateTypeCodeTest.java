package ericminio.javaoracle.domain;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;

public class GenerateTypeCodeTest {

    private String code;

    @Before
    public void generateCode() throws IOException {
        List<String> typeSpecification = Arrays.asList(
                "type custom_type as object\n",
                "(\n",
                "   field1 integer,\n",
                "   field2 varchar2(10)\n",
                ")"
        );
        GenerateTypeCode generateTypeCode = new GenerateTypeCode();
        code = generateTypeCode.please(typeSpecification);
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
    public void integerField() {
        assertThat(code, containsString("private Integer field1;"));
    }

    @Test
    public void stringField() {
        assertThat(code, containsString("private String field2;"));
    }

    @Test
    public void accessors() {
        assertThat(code, containsString(""
                + "    public Integer getField1() {\n"
                + "        return this.field1;\n"
                + "    }\n"
                + "    public void setField1(Integer field1) {\n"
                + "        this.field1 = field1;\n"
                + "    }\n"
                + "\n"
                + "    public String getField2() {\n"
                + "        return this.field2;\n"
                + "    }\n"
                + "    public void setField2(String field2) {\n"
                + "        this.field2 = field2;\n"
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
                "                this.getField1().equals(other.getField1())\n" +
                "                && this.getField2().equals(other.getField2())\n" +
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
                "                this.getField1().hashCode()\n" +
                "                + this.getField2().hashCode()\n" +
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
                "                + \" ]\";\n" +
                "    }"
        ));
    }

    @Test
    public void readSQLMethod() {
        assertThat(code, containsString("" +
                "    @Override\n" +
                "    public void readSQL(SQLInput stream, String typeName) throws SQLException {\n" +
                "        this.setField1(stream.readInt());\n" +
                "        this.setField2(stream.readString());\n" +
                "    }"
        ));
    }
}
