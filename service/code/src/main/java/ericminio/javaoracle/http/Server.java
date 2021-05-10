package ericminio.javaoracle.http;

import com.sun.net.httpserver.HttpServer;
import ericminio.javaoracle.support.LogSink;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server {

    private final Logger logger;
    private int port;
    private HttpServer httpServer;

    public Server(int port) {
        this.port = port;
        this.logger = new LogSink(true).getLogger();
    }

    public void stop() {
        httpServer.stop(1);
    }

    public void start() throws IOException {
        logger.log(Level.INFO, "Listening on port " + port);
        httpServer = HttpServer.create( new InetSocketAddress( port ), 0 );
        httpServer.createContext( "/", new Index());
        httpServer.createContext( "/zip", new DownloadZip());
        httpServer.start();
    }
}
