package server.server.console;

/**
 * Created by HNKNTOC on 10.01.2016.
 */
public interface CommandAttributes {
    String HELP = "help";
    String HELP_DESCRIPTION = "command help - displays the description of the command.";

    String GET_USER = "get_user";
    String GET_DESCRIPTION = "returns a list of all users on the server";

    String[] CONSOLE_COMMANDS = {HELP,GET_USER};

}
