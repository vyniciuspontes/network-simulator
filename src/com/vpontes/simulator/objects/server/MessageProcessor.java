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
import java.net.Socket;

/**
 *
 * @author Vynicius
 */
public class MessageProcessor implements Runnable {

    private Socket connectionSocket;

    public MessageProcessor(Socket connectionSocket) {
        this.connectionSocket = connectionSocket;
    }

    @Override
    public void run() {
        InputStream is = null;
        ObjectInput dataInputObject = null;
        IPV4Datagram datagram;

        try {
            is = connectionSocket.getInputStream();
            dataInputObject = new ObjectInputStream(is);

            while ((datagram = (IPV4Datagram) dataInputObject.readObject()) != null) {
                try {
                    System.out.print(datagram.toString() + "\n");
                    Node node = NodesCatalog.getInstace().getNode(datagram.getDestinationIPAddress());
                    if (node == null) {
                        System.out.println();
                        Router.getInstance().route(datagram);
                    }
                    dataInputObject.available();
                } catch (IllegalArgumentException ex) {
                    System.out.println("Erro ao tentar rotear datagrama: " + ex.toString());
                }
            }

        } catch (ClassNotFoundException | IOException ex) {
            System.out.println("Erro ao tentar ler o datagrama: " + ex.toString());
        }

    }

}
