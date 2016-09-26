package ua.khpi.diplom.socket;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerHandler {
    protected Socket client;
    protected PrintWriter out;

    public ServerHandler(Socket client) {
        this.client = client;
        try {
            this.out = new PrintWriter(client.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
