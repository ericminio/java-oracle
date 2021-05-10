package ericminio.javaoracle.http;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static ericminio.javaoracle.http.GetRequest.get;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class IndexTest {

    Server server;
    Integer port = 8005;

    @Before
    public void start() throws IOException {
        server = new Server(port);
        server.start();
    }
    @After
    public void stop() {
        server.stop();
    }

    @Test
    public void servesIndex() throws Exception {
        HttpResponse response = get( "http://localhost:" + port);

        assertThat(response.getStatusCode(), equalTo( 200));
        assertThat(response.getContentType(), equalTo("text/html"));
        assertThat(response.getBody(), containsString( "<!DOCTYPE html>"));
    }
}
