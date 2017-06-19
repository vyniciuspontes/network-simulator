package com.vpontes.simulator.objects;

import com.vpontes.simulator.objects.client.Client;
import com.vpontes.simulator.objects.server.Server;
import java.io.IOException;

/**
 *
 * @author Vynicius
 */
public class Simulator {

    private Server server;
    private final Client client;

    public Simulator() throws IOException {
        client = new Client();
    }

    public void startConnection(String virtualAddress) throws IOException, IllegalArgumentException {
        client.startConnection(virtualAddress);
    }

    public void sendMessage(String message) throws IOException {
        client.sendMessage(message);
    }

    public void createServer(Integer door, String name) throws IOException, InterruptedException {
        this.server = new Server(name, door);
        this.server.startServer();
    }

}
