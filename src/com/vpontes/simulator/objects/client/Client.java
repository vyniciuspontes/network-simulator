/*
*
* Simulador De Redes
* Redes I - Universidade Federal Fluminense
*
 */
package com.vpontes.simulator.objects.client;

import com.vpontes.simulator.objects.IPV4Datagram;
import com.vpontes.simulator.objects.Node;
import com.vpontes.simulator.objects.Router;
import com.vpontes.simulator.utils.IPV4Util;
import java.io.IOException;

/**
 *
 * @author Vynicius
 */
public class Client {

    private MessageDispatcher dipatcher;
    private Router router;
    private Node originNode;
    private String destinationAddress;

    public Client() throws IOException {
        dipatcher = new MessageDispatcher();
    }

    public void startConnection(String virtualAddress) throws IOException, IllegalArgumentException {

        //this.destinatioNode = NodesCatalog.getInstace().getNode(virtualAddress);
        if (!IPV4Util.verifyIp(virtualAddress)) {
            throw new IllegalArgumentException("IP invalido");
        }
        destinationAddress = virtualAddress;
        this.router = Router.getInstance();
        this.originNode = router.getOriginNode(virtualAddress);

        //if (this.destinatioNode == null) {
        //  throw new NumberFormatException("Impossivel iniciar conexão: node vizinho não encontrado (" + virtualAddress + ")");
        //}
        
        System.out.println("Iniciando conexão pela interface: " + originNode.getVirtualAddress());

        dipatcher.startConnection(this.originNode.getAddress(), this.originNode.getDoor());
    }

    public void sendMessage(String message) throws IOException {

        IPV4Datagram datagram = new IPV4Datagram(this.originNode.getAddress(), destinationAddress, message.getBytes());

        dipatcher.sendMessage(datagram);
    }

}
