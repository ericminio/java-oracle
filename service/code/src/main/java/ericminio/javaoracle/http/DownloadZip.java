package ericminio.javaoracle.http;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import ericminio.javaoracle.data.BuildIncoming;
import ericminio.javaoracle.data.Incoming;
import ericminio.javaoracle.domain.FileSet;
import ericminio.javaoracle.domain.GenerateClasses;
import ericminio.javaoracle.support.Stringify;
import ericminio.javaoracle.support.Zip;

import java.io.IOException;

public class DownloadZip implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String incomingBody = new Stringify().inputStream(exchange.getRequestBody());
        FormDataSet formDataSet = new FormDataProtocol().parse(incomingBody);
        String javaPackage = formDataSet.getByName("javaPackage").getValue();
        Incoming incoming = new BuildIncoming().from(formDataSet);
        FileSet fileSet = new GenerateClasses().from(incoming, javaPackage);
        byte[] bytes = new Zip().please(fileSet);
        exchange.getResponseHeaders().add("content-type", "application/zip");
        exchange.getResponseHeaders().add( "content-disposition", "attachment; filename=\"download.zip\"" );
        exchange.sendResponseHeaders(200, bytes.length);
        exchange.getResponseBody().write(bytes);
        exchange.close();
    }
}
