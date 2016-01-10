package server.server.console;

/**
 * Консольная команда выполняемая сервером.
 */
public interface ConsoleCommand {
    /**
     * Выполнение команды.
     * @param value передаёт значение команды.
     */
    void start(String value,int idClient);

    /**
     * Возвращает имя команды.
     * @return
     */
    String getOperator();
}
