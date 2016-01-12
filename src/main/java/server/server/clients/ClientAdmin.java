package server.server.clients;

import server.server.Server;

import java.io.BufferedReader;
import java.io.PrintWriter;

/**
 * Created by HNKNTOC on 12.01.2016.
 */
public class ClientAdmin extends ClientDefault {
    public ClientAdmin(String name, PrintWriter out, BufferedReader reader, int id, Server server) {
        super(name, out, reader, id, server);
    }

    @Override
    protected void executionCommand(String command, String value) {
        for(String key:server.getListAdminCommand().keySet()){
            if(key.equals(command)){
                server.getListAdminCommand().get(key).start(value,id);
                return;
            }
        }
        server.outMessages(id,"Server","No this command. Enter help for help.");
    }
}
