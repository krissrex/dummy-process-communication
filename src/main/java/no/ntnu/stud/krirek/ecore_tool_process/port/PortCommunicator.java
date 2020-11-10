package no.ntnu.stud.krirek.ecore_tool_process.port;

import no.ntnu.stud.krirek.ecore_tool_process.Communicator;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

/**
 * Talks via a port.
 *
 * You can connect using {@code telnet 127.0.0.1 <PORT>},<br>
 * e.g. {@code telnet 127.0.0.1 3212}.
 *
 * Then type a message and send with Enter.
 * Send {@value Client#EXIT_KEYWORD} or {@code ctrl D} to stop.
 *
 * <b>Note:</b> this does not speak HTTP or other protocols.
 */
public class PortCommunicator implements Communicator {


    @Override
    public void start(List<String> args) {
        int portOption = args.indexOf("-p");
        int port = 0;
        if (portOption != -1 && args.size() > portOption + 1) {
            port = Integer.parseInt(args.get(portOption + 1), 10);
        }

        if (port < 0 || port > 65535) {
            System.err.println("Invalid port: " + port);
            System.exit(1);
        } else if (port < 1024) {
            System.err.println("Warning: ports below 1024 are reserved, issues may arrise: " + port);
        }

        listen(port);
    }

    private void listen(int port) {
        try {
            // TODO: for multi-client, use while-loop and offload clients to threads.
            ServerSocket socket = new ServerSocket(port);
            System.out.printf("Listening at %s on port: %s%n", socket.getInetAddress().getHostAddress(), socket.getLocalPort());


            Socket clientSocket = socket.accept(); // blocking
            try (Client client = new Client(clientSocket)) {
                client.handleBlocking();
            } catch (Exception e) {
                System.err.println("Error in client: " + e.getMessage());
                e.printStackTrace();
            }

            socket.close();
        } catch (IOException e) {
            System.err.println("Error in socket: " + e.getMessage());
            e.printStackTrace();
        }

    }
}
