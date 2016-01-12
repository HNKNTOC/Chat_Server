package server.server.clients;

import server.server.Server;
import server.server.ServerSeting;

import java.io.BufferedReader;
import java.io.PrintWriter;

/**
 * Created by HNKNTOC on 12.01.2016.
 */
public class ClientFactory {
    public Client getClient(String name,PrintWriter out,BufferedReader reader,int id,Server server){
        Client client;
        if(checkNameAdmin(name)){
            client = new ClientAdmin(name,out,reader,id,server);
        }else {
            client = new ClientUser(name,out,reader,id,server);
        }

        return client;
    }

    /**
     * Проверка имени.
     * Если имя есть в listNameAdmins значит это админ.
     * @param name Имя пользователя.
     * @return true если имя принадлежит админу.
     */
    private boolean checkNameAdmin(String name){
        for(String s: ServerSeting.listNameAdmins){
            if(s.equals(name)){
                return true;
            }
        }
        return false;
    }
}
