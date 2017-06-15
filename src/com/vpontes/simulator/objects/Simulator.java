package com.vpontes.simulator.objects;

import java.io.IOException;

/**
 *
 * @author Vynicius
 */
public class Simulator {


    private Server server;
    private Client client;
    
    public void startConnection(String ip, Integer door){
        
    }
    
    public void closeCurrentConnection(){
        
    }
    
    
    public void createServer(Integer door, String name) throws IOException, InterruptedException{
        this.server = new Server(name, door);
        this.server.startServer();
    }

}
