package no.ntnu.stud.krirek.ecore_tool_process.port;

import no.ntnu.stud.krirek.ecore_tool_process.stdio.JsonPrinter;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * Talks over a Socket.
 *
 * End/disconnect with EOF (close connection from remote) or {@code "BYE"}.
 */
public class Client implements AutoCloseable {
    public static final String EXIT_KEYWORD = "BYE";

    private Socket connection;
    private JsonPrinter printer;

    public Client(Socket connection) {
        this.connection = connection;
    }

    public void handleBlocking() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
            printer = new JsonPrinter(new BufferedOutputStream(connection.getOutputStream()));

            String message;
            while ((message = reader.readLine()) != null) {
                printer.print(Map.of("message", "received", "echo", message));

                if (EXIT_KEYWORD.equals(message)){
                    break;
                }
            }
            System.out.printf("Client %s disconnected.", connection.getRemoteSocketAddress().toString());

        } catch (IOException e) {
            System.err.println("Error when handling client: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void close() throws Exception {
        this.connection.close();
    }
}
