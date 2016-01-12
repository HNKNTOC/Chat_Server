package server.server.console;

import server.server.ServerChat;

/**
 * Created by HNKNTOC on 10.01.2016.
 */
public class CommandGetUser implements ConsoleCommand {

    private ServerChat serverChat;

    public CommandGetUser(ServerChat serverChat) {
        this.serverChat = serverChat;
    }

    @Override
    public String getOperator() {
        return CommandAttributes.GET_USER;
    }

    @Override
    public void start(String value, int idClient) {
        if(value == null){
            serverChat.outMessages(idClient, "Server", serverChat.getListClient().values().toString());
        }else {
            switch (value){
                case "help":
                    serverChat.outMessages(idClient,"Server",CommandAttributes.GET_DESCRIPTION);
                    break;
                default:
                    serverChat.outMessages(idClient,"Server","inaccessible attribute");
            }
        }
    }
}
