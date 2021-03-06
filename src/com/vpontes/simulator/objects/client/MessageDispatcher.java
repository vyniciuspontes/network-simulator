/*
*
* Simulador De Redes
* Redes I - Universidade Federal Fluminense
*
 */
package com.vpontes.simulator.objects.client;

import com.vpontes.simulator.objects.IPV4Datagram;
import com.vpontes.simulator.objects.nodes.NodesCatalog;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Vynicius
 */
public class MessageDispatcher {

    private final int MAX_DATAGRAM_SIZE = 500;//65535;
    
    private Socket socket;
    private ObjectOutputStream outputStream;
    

    public MessageDispatcher() {}
    
    public void startConnection(String address, Integer door) throws IOException {
        
        this.socket = new Socket(address, door);
        this.outputStream = new ObjectOutputStream(socket.getOutputStream());
    }
    
    public void close() throws IOException{
        if(socket != null)
            socket.close();
    }

    public void sendMessage(IPV4Datagram datagram) throws IOException {
                
        if(NodesCatalog.getInstace().getNode(datagram.getDestinationIPAddress()) == null){
            datagram.decreaseTTL(1);
        }
        int datagramSize = getBytes(datagram).length;
        outputStream.reset();
        System.out.println(socket.getPort());
        if(datagramSize > MAX_DATAGRAM_SIZE){
            List<IPV4Datagram> fragments = slice(datagram, datagramSize);
            for (IPV4Datagram fragment : fragments) {
                
                outputStream.writeObject(fragment);
            }
        }else {
            outputStream.writeObject(datagram);
        }
    }
    
    /**
     * Fragmenta a mensagem em uma lista de datagramas
     * @param datagram
     * @param datagramSize
     * @return 
     */
    private List<IPV4Datagram> slice(IPV4Datagram datagram, int datagramSize){
        
        int contentSize = datagram.getContent().length;
        int headerSize = datagramSize - contentSize;
        
        int contentLimit = MAX_DATAGRAM_SIZE - headerSize;
        
        int datagramNumber = contentSize / contentLimit;
        int rest = datagramSize % contentLimit;
        if(rest != 0)
            datagramNumber++;
        
        List<IPV4Datagram> datagrams = new ArrayList<>();
        int endOfArray;
        for (int i = 0; i < datagramNumber; i++) {
            endOfArray = (i * contentLimit) + contentLimit;
            byte[] array = Arrays.copyOfRange(datagram.getContent(), i * contentLimit, contentSize < endOfArray ? contentSize : endOfArray);
            datagrams.add(new IPV4Datagram(datagram.getSourceIPAddress(), datagram.getDestinationIPAddress(), array));
        }
        
        return datagrams;
    }
    
    private byte[] getBytes(Object object) throws IOException{
        //OutputStream para um array de bytes
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        //Output dos bytes de um objeto em outro OutputStream
        ObjectOutput out = new ObjectOutputStream(bos);
        out.writeObject(object);
        out.flush();
        return bos.toByteArray();
    }
}
