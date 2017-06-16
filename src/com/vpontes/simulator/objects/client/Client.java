/*
*
* Simulador De Redes
* Redes I - Universidade Federal Fluminense
*
 */
package com.vpontes.simulator.objects.client;

import com.vpontes.simulator.objects.IPV4Datagram;
import com.vpontes.simulator.objects.Node;
import com.vpontes.simulator.objects.NodesCatalog;
import com.vpontes.simulator.objects.Router;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Vynicius
 */
public class Client {

    private MessageDispatcher dipatcher;
    private Router router;
    private Node originNode;
    private Node destinatioNode;

    public Client() throws IOException {
        dipatcher = new MessageDispatcher();
    }

    public void startConnection(String virtualAddress) {

        try {
            this.destinatioNode = NodesCatalog.getInstace().getNode(virtualAddress);
            this.router = Router.getInstance();
            this.originNode = router.getOriginNode(virtualAddress);
            if (this.destinatioNode == null) {
                throw new RuntimeException("Impossivel iniciar conexão: node vizinho não encontrado (" + virtualAddress + ")");
            }
            
            System.out.println("Iniciando conexão com: " + virtualAddress + " pela interface: " + originNode.getVirtualAddress());
            
            dipatcher.startConnection(this.destinatioNode.getAddress(), this.destinatioNode.getDoor());
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, "Não foi possível estabelecer conexão com o endereço virtual: " + destinatioNode.getSubnetAddress(), ex);
        }
    }

    public void sendMessage(String message) throws IOException {
        
        IPV4Datagram datagram = new IPV4Datagram(this.originNode.getAddress(), this.destinatioNode.getVirtualAddress(), message.getBytes());
        
        dipatcher.sendMessage(datagram);
    }

}
