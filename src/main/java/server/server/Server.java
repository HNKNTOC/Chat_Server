package server.server;

import server.server.clients.Client;
import server.server.console.ConsoleCommand;

import java.util.HashMap;

/**
 * Сервер
 */
public interface Server {
    /**
     * Запуск сервера
     */
    void start();

    /**
     * Остоновка сервера
     */
    void stop();

    /**
     * Отсылает сообщение всем клиентам.
     * @param nameAuthor имя отправителя
     * @param content содержание сообщения
     */
    void delivery(String nameAuthor,String content);

    /**
     * Отсылает сообщение клиенту.
     * @param idUser id клиента
     * @param nameAuthor имя отправителя
     * @param content содержание сообщения
     * @return true если сообщение отправлено, false если id нет в списке.
     */
    boolean outMessages(int idUser,String nameAuthor, String content);

    /**
     * Исключает клиент по его id.
     * @param id Индефекатор клиента.
     * @return false если не удалось найти пользователя с данным id;
     */
    boolean exceptionUser(int id);

    HashMap<Integer, Client> getListClient();

    HashMap<String, ConsoleCommand> getListUserCommand();

    HashMap<String, ConsoleCommand> getListAdminCommand();
}
