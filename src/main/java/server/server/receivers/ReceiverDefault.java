package server.server.receivers;

import server.server.clients.MessageListener;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.SocketException;
import java.util.ArrayList;

/**
 * Created by HNKNTOC on 11.01.2016.
 */
public class ReceiverDefault implements Receiver,Runnable {
    private ArrayList<MessageListener> listListener = new ArrayList<>();
    private BufferedReader reader;

    public ReceiverDefault(BufferedReader reader) {
        this.reader = reader;
    }

    @Override
    public void run() {
        String txt;
        try {
            while (true){
                    txt = reader.readLine();
                for(MessageListener listener:listListener){
                    listener.inputMessage(txt);
                }
            }
        }catch (SocketException e){
            stopListener();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stopListener() {
        for(MessageListener listener:listListener){
            listener.stopListener();
        }

        try {
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addMessageListener(MessageListener messageHandler) {
        listListener.add(messageHandler);
    }
}
