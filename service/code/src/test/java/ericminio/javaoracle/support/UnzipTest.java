package ericminio.javaoracle.support;

import ericminio.javaoracle.domain.FileInfo;
import ericminio.javaoracle.domain.FileSet;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class UnzipTest {

    @Test
    public void returnsExpectedFileSet() throws IOException {
        FileSet input = new FileSet();
        input.add(new FileInfo("one.txt", "content #1"));
        input.add(new FileInfo("two.txt", "content #2"));
        byte[] zip = new Zip().please(input);

        FileSet output = new Unzip().please(zip);
        assertThat(output.size(), equalTo(2));
        assertThat(output.get(0).getFileName(), equalTo("one.txt"));
        assertThat(output.get(0).getContent(), equalTo("content #1"));
        assertThat(output.get(1).getFileName(), equalTo("two.txt"));
        assertThat(output.get(1).getContent(), equalTo("content #2"));
    }
}
