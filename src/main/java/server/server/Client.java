package server.server;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;

/**
 *
 */
public class Client implements Runnable {
    private Socket socket;
    private ServerChat server;
    private PrintWriter out;
    private String name;
    private int idClient;

    public Client(Socket socket, ServerChat server, int idClient) {
        this.socket = socket;
        this.server = server;
        this.idClient = idClient;
    }

    public int getIdClient() {
        return idClient;
    }

    public String getName() {
        return name;
    }

    public PrintWriter getOut() {
        return out;
    }

    @Override
    public void run() {
        try {

            out = new PrintWriter(socket.getOutputStream(),true);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));

            name = in.readLine();

            server.delivery("Server", "new user " + name);

            String s;
            while (true) {
                s = in.readLine();
                processingMessage(s);
            }
        }catch (SocketException e){
            server.exceptionUser(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void processingMessage(String txt){
        //Проверка если первый символ / то пришла команда
        if(server.checkCommand(txt)){
            //выполнение команды
            server.newCommand(txt,idClient);
        }else {
            // отправка сообщения.
            server.delivery(name, txt);
        }
    }

    public void close(){
        try {
            socket.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return name;
    }
}
