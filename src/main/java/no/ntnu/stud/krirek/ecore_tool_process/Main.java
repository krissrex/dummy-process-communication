package no.ntnu.stud.krirek.ecore_tool_process;


import no.ntnu.stud.krirek.ecore_tool_process.port.PortCommunicator;
import no.ntnu.stud.krirek.ecore_tool_process.stdio.StdioCommunicator;

import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // To test different ways of communication,
        // the program can be started with:
        // - std io
        // - socket file
        // - tcp port

        Communicator communicator = null;
        if (args.length >= 1) {
            String communicationMode = args[0];
            switch (communicationMode) {
                case "stdio":
                    communicator = new StdioCommunicator();
                    break;
                case "socket":
                    //TODO: implement. Probably with https://github.com/kohlschutter/junixsocket
                    break;
                case "port":
                    communicator = new PortCommunicator();
                    break;
                default:
                    System.err.printf("Invalid option: %s. Must be stdio, socket or port.%n", communicationMode);
            }
        } else {
            System.err.println("Missing arguments! Got: " + String.join(" ", args));
        }

        if (communicator != null) {
            List<String> communicatorArgs = Arrays.asList(args).subList(1, args.length);
            communicator.start(communicatorArgs);
        }
    }
}
