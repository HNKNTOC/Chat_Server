package server;


import server.server.ServerChat;

/**
 * Created by HNKNTOC on 04.01.2016.
 */
public class MainServer {
    public static void main(String[] args) {
        ServerChat serverChat = new ServerChat();
        serverChat.start();
    }
}
