package server.server.clients;

import java.io.PrintWriter;

/**
 * Хранит всебе информацию о клиенте.
 */
public interface Client {
    int getId();
    String getName();
    PrintWriter getOut();
    void close();
}
