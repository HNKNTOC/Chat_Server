package server.server.clients;

/**
 * Слушает сообщения.
 */
public interface MessageListener {

    /**
     * Принимает сообщение и обрабатывает его.
     * @param content текст сообщения.
     */
    void inputMessage(String content);

    /**
     * Метод вызывается если слушать сообщения нельзя.
     */
    void stopListener();
}
