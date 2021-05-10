package ericminio.javaoracle.main;

import ericminio.javaoracle.http.Server;

public class StartServer {

    public static void main(String[] args) {
        try {
            Integer port = 8080;
            String candidate = System.getProperty("PORT");
            if (candidate != null) {
                port = Integer.parseInt(candidate);
            }
            Server server = new Server(port);
            server.start();
        }
        catch (Exception raised) {
            raised.printStackTrace();
        }
    }
}
