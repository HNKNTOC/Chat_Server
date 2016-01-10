package server.server.console;

import server.server.ServerChat;

import java.util.Arrays;

/**
 * Created by HNKNTOC on 09.01.2016.
 */
public class CommandHelp implements ConsoleCommand {

    ServerChat serverChat;

    public CommandHelp(ServerChat serverChat) {
        this.serverChat = serverChat;
    }

    @Override
    public void start(String value,int idClient) {
        String txt = "command help - displays the description of the command." +
                " List command-"+ Arrays.toString(CommandAttributes.CONSOLE_COMMANDS);
        serverChat.outMessages(idClient,"Server",txt);
    }

    @Override
    public String getOperator() {
        return CommandAttributes.HELP;
    }
}
