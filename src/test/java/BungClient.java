import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 * Клиент для теста сервера
 */
public class BungClient {
    Socket socket;
    PrintWriter printWriter;
    BufferedReader reader;

    public boolean connect(){
        try {
            socket = new Socket("127.0.0.1",5050);

            printWriter = new PrintWriter(socket.getOutputStream(),true);
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            printNewMessage("BungClient");

            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    String name,content,date;
                    try {
                        while (true){

                            while ((name=reader.readLine())!=null){
                                content=reader.readLine();
                                date=reader.readLine();
                                System.out.println(name+":"+content+"   "+date);
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            thread.start();

            Scanner scanner = new Scanner(System.in);

            while (scanner.hasNext()){
                System.out.println(scanner.nextLine());
            }


        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public void printNewMessage(String txt){
        printWriter.println(txt);
    }

}
