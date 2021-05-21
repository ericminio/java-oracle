package ericminio.javaoracle.data;

import ericminio.javaoracle.domain.Incoming;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class BuildIncomingTest {

    private BuildIncoming buildIncoming;

    @Before
    public void sut() {
        buildIncoming = new BuildIncoming();
    }

    @Test
    public void resistsNoType() {
        Incoming incoming = buildIncoming.from(Arrays.asList(
                "create package any_package as",
                "   function any_function return number;",
                "end any_package;"
        ), new ArrayList<String>());

        assertThat(incoming.getTypeSpecifications().size(), equalTo(0));
        assertThat(incoming.getTypeNames().size(), equalTo(0));
    }
}
