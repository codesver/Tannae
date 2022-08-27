package codesver.tannae.network;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;

public class Network {
    public static ServiceApi service;
    public static Socket socket;

    static {
        try {
            socket = IO.socket("http://118.219.190.205:8080/");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }
}
