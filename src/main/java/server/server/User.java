package server.server;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 *
 */
public class User implements Runnable {
    private Socket socket;
    private ServerChat server;
    private PrintWriter out;
    private String name;

    public User(Socket socket,ServerChat server) {
        this.socket = socket;
        this.server = server;
    }

    public PrintWriter getOut() {
        return out;
    }

    @Override
    public void run() {
        try {

            out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));

            name = in.readLine();

            server.delivery("Server","new user " + name,"data test");

            String s;
            while (true) {
                s = in.readLine();
                server.delivery(name,s,"data test");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
