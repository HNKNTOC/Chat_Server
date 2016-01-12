package server.server.clients;

import server.server.Server;
import server.server.receivers.Receiver;
import server.server.receivers.ReceiverDefault;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Хранит в себе информацию о клиенте.
 * Обрабатывает пришедшие от клиента сообщения.
 */
public class ClientDefault implements Client,MessageListener {
    protected Server server;
    protected int id;
    protected String name;
    protected PrintWriter printWriter;
    protected ReceiverDefault receiver;

    public ClientDefault(String name,PrintWriter out,BufferedReader reader,int id,Server server) {
        this.name=name;
        this.id = id;
        this.server=server;
        this.printWriter = out;

        receiver = new ReceiverDefault(reader);

        receiver.addMessageListener(this);
        Thread thread = new Thread(receiver);
        thread.start();

    }

    /**
     * Стандартная обработка сообщения.
     * @param content текст сообщения.
     */
    @Override
    public void inputMessage(String content) {
        //Проверка если первый символ / то пришла команда
        if(checkCommand(content)){
            //выполнение команды
            newConsoleCommand(content);
        }else {
            // отправка сообщения.
            server.delivery(name, content);
        }
    }


    /**
     * Разделяет сообшение на имя комманды и значение.
     * И вызывает executionCommand.
     * @param content текст команды.
     */
    protected void newConsoleCommand(String content){
        String command,value;

        content = String.copyValueOf(content.toCharArray(),1,content.length()-1);
        String[] strings = content.trim().split(" ");

        if(strings.length==2){
            command = strings[0];
            value = strings[1];
        }else {
            command = strings[0];
            value = null;
        }
        executionCommand(command,value);
    }

    /**
     * Метод перехватчик для обработки команды.
     * @param command имя команды
     * @param value значение команды
     */
    protected void executionCommand(String command,String value){
    }

    @Override
    public void stopListener() {
        server.exceptionUser(id);
    }

    public boolean checkCommand(String txt){
        return (txt.charAt(0)=='/');
    }

    @Override
    public void close() {
        printWriter.close();
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public PrintWriter getOut() {
        return printWriter;
    }

    @Override
    public String toString() {
        return name;
    }
}
