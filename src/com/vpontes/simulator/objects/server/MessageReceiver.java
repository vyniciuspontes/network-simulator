/*
*
* Simulador De Redes
* Redes I - Universidade Federal Fluminense
*
 */
package com.vpontes.simulator.objects.server;

import com.vpontes.simulator.objects.IPV4Datagram;
import com.vpontes.simulator.objects.Node;
import com.vpontes.simulator.objects.NodesCatalog;
import com.vpontes.simulator.objects.Router;
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

    public MessageReceiver(Integer door) throws IOException {
        this.welcomeSocket = new ServerSocket(door);
    }

    private void proccessConnection(Socket connectionSocket) throws IOException, ClassNotFoundException {

        /*
        ByteArrayOutputStream byteArrayOutput = new ByteArrayOutputStream();
        byte[] data = new byte[1024];

        while (is.read(data) != -1) {
            byteArrayOutput.write(data);
        }
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutput.toByteArray());*/
        

        //IPV4Datagram message1 = (IPV4Datagram) dataInputObject.readObject();
        //System.out.println(new String(message1.getContent()));
        
        InputStream is = connectionSocket.getInputStream();
        ObjectInput dataInputObject = new ObjectInputStream(is);        
        IPV4Datagram datagram;
        
        while ((datagram = (IPV4Datagram) dataInputObject.readObject()) != null) {
            System.out.print(datagram.toString() + "\n");
            Node node = NodesCatalog.getInstace().getNode(datagram.getDestinationIPAddress());
            if(node == null){
                Router.getInstance().route(datagram);
            }
        }
        
        
    }

    @Override
    public void run() {
        while (true) {
            try {
                Socket connectionSocket = this.welcomeSocket.accept();
                this.proccessConnection(connectionSocket);
            } catch (IOException | ClassNotFoundException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE,
                        "", ex);
            }
        }
    }

}
