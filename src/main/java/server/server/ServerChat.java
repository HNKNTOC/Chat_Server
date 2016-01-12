package server.server;

import server.server.clients.Client;
import server.server.clients.ClientDefault;
import server.server.clients.ClientFactory;
import server.server.console.CommandGetUser;
import server.server.console.CommandHelp;
import server.server.console.ConsoleCommand;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

/**
 * Server
 */
public class ServerChat implements Server {

    private HashMap<Integer, Client> listClient = new HashMap<>();
    private HashMap<String,ConsoleCommand> listUserCommand = new HashMap<>();
    private HashMap<String,ConsoleCommand> listAdminCommand = new HashMap<>();
    private int id=0;

    public ServerChat() {
        addUserCommand(new CommandHelp(this));

        addAdminCommand(new CommandHelp(this));
        addAdminCommand(new CommandGetUser(this));
    }

    @Override
    public HashMap<Integer, Client> getListClient() {
        return listClient;
    }

    @Override
    public HashMap<String, ConsoleCommand> getListUserCommand() {
        return listUserCommand;
    }

    @Override
    public HashMap<String, ConsoleCommand> getListAdminCommand() {
        return listAdminCommand;
    }

    @Override
    public void start(){
        try {
            System.out.println("ServerChat start....");
            ServerSocket serverSocket = new ServerSocket(5050);

            while (true){
                Socket socket = serverSocket.accept();
                System.out.println("New connect:" + socket.getInetAddress().toString());

                PrintWriter printWriter = new PrintWriter(socket.getOutputStream(),true);
                BufferedReader reader =
                        new BufferedReader(new InputStreamReader(socket.getInputStream()));

                String name = reader.readLine();

                ClientFactory factory = new ClientFactory();

                Client client = factory.getClient(name,printWriter,reader,id,this);

                listClient.put(id, client);
                delivery("Server","new user "+client.getName());
                id++;

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Отсылает сообщение всем клиентам.
     * @param nameAuthor имя отправителя
     * @param content содержание сообщения
     */
    @Override
    public void delivery(String nameAuthor, String content){
        String time = getTime();
        System.out.println(nameAuthor + ":" + content + ", " + time);
        for (Client user: listClient.values()){
            PrintWriter out = user.getOut();
            out.println(nameAuthor);
            out.println(content);
            out.println(time);
        }
    }

    /**
     * Отсылает сообщение клиенту.
     * @param idUser id клиента
     * @param nameAuthor имя отправителя
     * @param content содержание сообщения
     * @return true если сообщение отправлено, false если id нет в списке.
     */
    @Override
    public boolean outMessages(int idUser,String nameAuthor, String content){
        Client user = listClient.get(idUser);
        if(user==null){
            return false;
        }
        PrintWriter out = user.getOut();
        out.println(nameAuthor);
        out.println(content);
        out.println(getTime());
        out.flush();
        return true;
    }

    @Override
    public void stop() {

    }

    /**
     * Исключает клиент
     * @param id Индефекатор клиента.
     * @return
     */
    @Override
    public boolean exceptionUser(int id) {
        Client client = listClient.get(id);
        if(client==null){
            return false;
        }
        client.close();
        listClient.remove(id);
        delivery("Server", "Client " + client.getName() + " disconnected");
        return false;
    }

    public void addUserCommand(ConsoleCommand command){
        listUserCommand.put(command.getOperator(), command);
    }

    public void addAdminCommand(ConsoleCommand command){
        listAdminCommand.put(command.getOperator(), command);
    }

    public String getTime(){
        return new SimpleDateFormat("h:mm").format(new Date());
    }

}
