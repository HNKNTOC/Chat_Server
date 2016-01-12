package server.server.clients;

import server.server.Server;

import java.io.BufferedReader;
import java.io.PrintWriter;

/**
 * Хранит информацию о пользователе.
 * Обрабатывает консольные команды пользователя.
 */
public class ClientUser extends ClientDefault {
    public ClientUser(String name, PrintWriter out, BufferedReader reader, int id, Server server) {
        super(name, out, reader, id, server);
    }

    /**
     * Проверяет есть ли command в listUserCommand
     * если команды нет сообщает клиенту об отсутствии команды.
     * (также проверяет есть ли команда в listAdminCommand
     *  если да сообшает клиенту о том что унего нет доступа к этой команде)
     * если команда есть выполняет её.
     *
     * @param command имя команды
     * @param value значение команды
     */
    @Override
    protected void executionCommand(String command, String value) {
        for(String key:server.getListUserCommand().keySet()){
            if(key.equals(command)){
                server.getListUserCommand().get(key).start(value,id);
                return;
            }
        }
        for(String key:server.getListAdminCommand().keySet()){
            if(key.equals(command)){
                server.outMessages(id,"Server","This command is not available to you.");
                return;
            }
        }
        server.outMessages(id,"Server","No this command. Enter help for help.");
    }
}
