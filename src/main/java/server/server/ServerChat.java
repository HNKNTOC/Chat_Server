package server.server;

import server.server.console.CommandGetUser;
import server.server.console.CommandHelp;
import server.server.console.ConsoleCommand;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

/**
 * Server
 */
public class ServerChat {

    public HashMap<Integer,Client> listClient = new HashMap<>();
    public HashMap<String,ConsoleCommand> listCommand = new HashMap<>();
    public int id=0;

    public ServerChat() {
        addCommand(new CommandHelp(this));
        addCommand(new CommandGetUser(this));
    }

    public void start(){
        try {
            System.out.println("ServerChat start....");
            ServerSocket serverSocket = new ServerSocket(5050);

            while (true){
                Socket socket = serverSocket.accept();
                System.out.println("New connect:"+socket.getInetAddress().toString());

                Client client = new Client(socket,this,id);
                Thread thread = new Thread(client);
                thread.start();

                listClient.put(id, client);
                id++;

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void exceptionUser(Client user){
        user.close();
        listClient.remove(user.getIdClient());
        delivery("Server", "Client " + user.getName() + " disconnected");
    }

    /**
     * Отсылает сообщение всем клиентам.
     * @param nameAuthor имя отправителя
     * @param content содержание сообщения
     */
    public void delivery(String nameAuthor, String content){
        System.out.println(nameAuthor + ":" + content + ", " + getTime());
        for (Client user: listClient.values()){
            user.getOut().println(nameAuthor);
            user.getOut().println(content);
            user.getOut().println(getTime());
        }
    }

    /**
     * Отсылает сообщение клиенту.
     * @param idUser id клиента
     * @param nameAuthor имя отправителя
     * @param content содержание сообщения
     * @return true если сообщение отправлено, false если id нет в списке.
     */
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

    /**
     * Проверяет наченается ли строка с /.
     * @param s
     * @return true если первый символ в строке / false если нет
     */
    public boolean checkCommand(String s){
        return (s.charAt(0)=='/');
    }

    public void newCommand(String s,int idClient){

        String command,value;

        s = String.copyValueOf(s.toCharArray(),1,s.length()-1);
        String[] strings = s.trim().split(" ");

        if(strings.length==2){
            command = strings[0];
            value = strings[1];
        }else {
            command = strings[0];
            value = null;
        }

        for(String key:listCommand.keySet()){
            if(key.equals(command)){
                listCommand.get(key).start(value,idClient);
                return;
            }
        }
        outMessages(idClient,"Server","Command not found");

    }

    public void addCommand(ConsoleCommand command){
        listCommand.put(command.getOperator(),command);
    }

    public String getTime(){
        return new SimpleDateFormat("h:mm").format(new Date());
    }

}
