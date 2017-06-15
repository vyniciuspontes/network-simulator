/*
*
* Simulador De Redes
* Redes I - Universidade Federal Fluminense
*
 */
package com.vpontes.simulator.objects;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Vynicius
 */
public class MessageProcessor implements Runnable{

    private final ServerSocket welcomeSocket;

    public MessageProcessor(Integer door) throws IOException {
        this.welcomeSocket = new ServerSocket(door);
    }
    
    private void proccessConnection(Socket connectionSocket) throws IOException {
        Scanner scanner = new Scanner(connectionSocket.getInputStream());
        while (scanner.hasNextLine()) {
            String next = scanner.nextLine();
            System.out.println("Mensagem Recebida: " + next);
        }
    }
    
    @Override
    public void run() {
        while (true) {
            try {
                Socket connectionSocket = this.welcomeSocket.accept();
                this.proccessConnection(connectionSocket);
            } catch (IOException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, 
                    "Can't start thread", ex);
            }
        }
    }
    
}
