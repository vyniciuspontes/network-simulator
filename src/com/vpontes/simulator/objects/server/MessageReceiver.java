/*
*
* Simulador De Redes
* Redes I - Universidade Federal Fluminense
*
 */
package com.vpontes.simulator.objects.server;

import com.vpontes.simulator.objects.IPV4Datagram;
import com.vpontes.simulator.objects.nodes.Node;
import com.vpontes.simulator.objects.nodes.NodesCatalog;
import com.vpontes.simulator.objects.router.Router;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Vynicius
 */
public class MessageReceiver implements Runnable {

    private final ServerSocket welcomeSocket;

    public MessageReceiver(Integer door) throws IOException, IllegalArgumentException {
        this.welcomeSocket = new ServerSocket(door);
    }

    @Override
    public void run() {
        while (true) {
            try {
                Socket connectionSocket = this.welcomeSocket.accept();
                Thread t = new Thread(new MessageProcessor(connectionSocket));
                t.start();
            } catch (IOException ex) {
                Logger.getLogger(MessageReceiver.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
