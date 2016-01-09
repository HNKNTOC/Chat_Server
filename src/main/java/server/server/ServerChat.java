package server.server;



import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Server
 */
public class ServerChat {

    public ArrayList<User> listUsers = new ArrayList<>();

    public void start(){
        try {
            System.out.println("ServerChat start....");
            ServerSocket serverSocket = new ServerSocket(5050);

            while (true){
                Socket socket = serverSocket.accept();
                System.out.println("New connect:"+socket.getInetAddress().toString());

                User user = new User(socket,this);
                Thread thread = new Thread(user);
                thread.start();

                listUsers.add(user);

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void exceptionUser(User user){
        user.close();
        listUsers.remove(user);
        delivery("Server","User "+user.getName()+" disconnected","data test");
    }

    public void delivery(String nameAuthor, String content, String data){
        System.out.println(nameAuthor + ":" + content + ", " + getTime());
        for (User user:listUsers){
            user.getOut().println(nameAuthor);
            user.getOut().println(content);
            user.getOut().println(getTime());
        }
    }

    public String getTime(){
        return new SimpleDateFormat("h:mm").format(new Date());
    }

}
