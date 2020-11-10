package no.ntnu.stud.krirek.ecore_tool_process.stdio;

import no.ntnu.stud.krirek.ecore_tool_process.Communicator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

/**
 * Talks over stdin and stdout.
 */
public class StdioCommunicator implements Communicator {

    private JsonPrinter printer = new JsonPrinter();

    @Override
    public void start(List<String> args) {
        printer.print(Map.of("message", "starting", "status", "listening on stdio. Send EOF (ctrl D or ctrl C) to stop."));
        readStdin();
        printer.print(Map.of("message", "stopping"));
    }

    private void readStdin() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8));

            String message;
            while ((message = reader.readLine()) != null) {
                printer.print(Map.of("message", "received", "echo", message));
            }
        } catch (IOException e) {
            printer.print(Map.of("error", e.getMessage()));
            e.printStackTrace();
        }
    }
}
