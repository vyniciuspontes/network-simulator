package com.vpontes.simulator.objects.server;

import java.io.IOException;

/**
 *
 * @author Vynicius
 */
public class Server {

    private final String name;
    private final Integer door;
    private Thread serverThread;

    public Server(String name, Integer door) {
        this.name = name;
        this.door = door;
    }

    public String getName() {
        return name;
    }

    public Integer getDoor() {
        return door;
    }

    public void startServer() throws IOException, InterruptedException {
        this.serverThread = new Thread(new MessageReceiver(this.door));
        this.serverThread.start();
        Thread.sleep(500);
        System.out.println("Server " + this.name + " executando na porta " 
            + this.door);
    }
    
    public void stopServer(){
        this.serverThread.interrupt();
    }
}
