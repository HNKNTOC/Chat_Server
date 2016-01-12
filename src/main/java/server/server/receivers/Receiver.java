package server.server.receivers;

import server.server.clients.MessageListener;

/**
 *  Принимает сообщения и отправляет их MessageListener.
 */
public interface Receiver {
    /**
     * Добавить слушателя.
     * @param messageHandler слушатель.
     */
    void addMessageListener(MessageListener messageHandler);

    /**
     * Останавливает слушание.
     */
    void stopListener();
}
