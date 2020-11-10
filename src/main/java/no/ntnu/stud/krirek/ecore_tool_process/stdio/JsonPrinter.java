package no.ntnu.stud.krirek.ecore_tool_process.stdio;

import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Makes printing maps as JSON easier.
 * Automatically adds a timestamp as {@code "time"}.
 */
public class JsonPrinter {

    private final PrintStream out;

    public JsonPrinter() {
        out = System.out;
    }

    public JsonPrinter(OutputStream out) {
        this.out = new PrintStream(out, true, StandardCharsets.UTF_8);
    }

    public void print(Map<String, String> message) {
        List<String> entries = message.entrySet().stream().map(entry -> {
            return String.format("\"%s\": \"%s\"", sanitize(entry.getKey()), sanitize(entry.getValue()));
        }).collect(Collectors.toList());
        entries.add(0, timestamp());

        String body = String.join(", ", entries);
        String json = "{ " + body + " }";
        out.println(json);
    }

    private String sanitize(String message) {
        return message.replace("\"", "\\\"");
    }

    private String timestamp() {
        return String.format("\"time\": \"%s\"", Calendar.getInstance().toInstant().toString());
    }
}
