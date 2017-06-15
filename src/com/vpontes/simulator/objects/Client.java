/*
*
* Simulador De Redes
* Redes I - Universidade Federal Fluminense
*
 */
package com.vpontes.simulator.objects;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Vynicius
 */
public class Client {

    private final String NODE_FILE_LOCATION = "nodes.txt";
    private List<Node> nodes;
    private MessageDispatcher dipatcher;

    public Client() throws IOException {
        this.readNodeFile();
        dipatcher = new MessageDispatcher();
    }

    public void startConnection(String address, Integer door) throws IOException {

        Node currentNode = nodes.stream()
                .filter(x -> x.getVirtualAddress().equals(address) && x.getVirtualDoor().equals(door))
                .findAny()
                .orElse(null);

        if (currentNode == null) {
            throw new RuntimeException("Impossivel iniciar conexão: node vizinho não encontrado");
        }

        dipatcher.startConnection(address, door);
    }

    public void sendMessage(String message) throws IOException {

        dipatcher.sendMessage(message);
    }

    private void readNodeFile() throws IOException {

        this.nodes = new ArrayList<>();

        System.out.println("Lendo o arquivo: " + NODE_FILE_LOCATION);

        Scanner scanner = new Scanner(new File(NODE_FILE_LOCATION));

        while (scanner.hasNextLine()) {
            String[] nextLine = scanner.nextLine().split(" ");

            Node n = new Node(nextLine[0], Integer.valueOf(nextLine[1]));
            //System.out.println(n);
            this.nodes.add(n);
        }
    }

}
