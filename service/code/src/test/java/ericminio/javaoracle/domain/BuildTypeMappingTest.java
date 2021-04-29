package ericminio.javaoracle.domain;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class BuildTypeMappingTest {

    @Test
    public void emptyByDefault() {
        assertThat(new BuildTypeMapping().please(new ArrayList<String>()), equalTo(""));
    }

    @Test
    public void notNeededForVarchar() {
        assertThat(new BuildTypeMapping().please(Arrays.asList("varchar2")), equalTo(""));
    }

    @Test
    public void notNeededForInteger() {
        assertThat(new BuildTypeMapping().please(Arrays.asList("integer")), equalTo(""));
    }

    @Test
    public void notNeededForNumber() {
        assertThat(new BuildTypeMapping().please(Arrays.asList("number")), equalTo(""));
    }

    @Test
    public void notNeededForDate() {
        assertThat(new BuildTypeMapping().please(Arrays.asList("date")), equalTo(""));
    }

    @Test
    public void neededForCustomType() {
        assertThat(new BuildTypeMapping().please(Arrays.asList("any_type")), equalTo("" +
                "        connection.getTypeMap().put(AnyType.NAME, AnyType.class);\n"
        ));
    }
}
