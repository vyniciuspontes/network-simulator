/*
*
* Simulador De Redes
* Redes I - Universidade Federal Fluminense
*
 */
package com.vpontes.simulator.objects.nodes;

import com.vpontes.simulator.objects.client.Client;
import com.vpontes.simulator.utils.IPV4Util;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Classe responsável por fazer encapsular e recuperar as interfaces do projeto
 * @author Vynicius
 */
public class NodesCatalog {

    private final String PHYSICAL_ADDRESSES_FILE_LOCATION = "physical-addresses.txt";
    private final String VIRTUAL_ADDRESSES_FILE_LOCATION = "virtual-addresses.txt";

    private static NodesCatalog instance;
    private List<Node> nodes;

    private NodesCatalog() {
        this.readPhysicalAddressFile();
        this.readVirtualAddressFile();
    }

    static public NodesCatalog getInstace() {

        if (instance == null) {
            instance = new NodesCatalog();
        }

        return instance;
    }

    public Node getNode(String virtualAddress) {
        Node currentNode = nodes.stream()
                .filter(x -> x.getVirtualAddress().equals(virtualAddress))
                .findAny()
                .orElse(null);

        return currentNode;
    }
    
    public Node getNode(int index) {
        Node currentNode = nodes.stream()
                .filter(x -> x.getIndex().equals(index))
                .findAny()
                .orElse(null);

        return currentNode;
    }
    
    private void readPhysicalAddressFile() {
        try {
            this.nodes = new ArrayList<>();

            System.out.println("Lendo o arquivo: " + PHYSICAL_ADDRESSES_FILE_LOCATION);

            Scanner scanner = new Scanner(new File(PHYSICAL_ADDRESSES_FILE_LOCATION));
            int count = 0;
            while (scanner.hasNextLine()) {
                String[] nextLine = scanner.nextLine().split(" ");

                Node n = new Node();
                n.setAddress(nextLine[0]);
                n.setDoor(Integer.valueOf(nextLine[1]));
                n.setIndex(count);
                //System.out.println(n);
                this.nodes.add(n);
                count++;
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, "Erro ao tentar ler o arquivo de endereços fisicos", ex);
        }
    }

    private void readVirtualAddressFile() {

        try {
            System.out.println("Lendo o arquivo: " + VIRTUAL_ADDRESSES_FILE_LOCATION);

            Scanner scanner = new Scanner(new File(VIRTUAL_ADDRESSES_FILE_LOCATION));

            nodes.forEach((node) -> {
                String[] nextLine = scanner.nextLine().split(" ");
                node.setVirtualAddress(nextLine[0]);
                node.setVirtualSubmask(nextLine[1]);
                node.setSubnetAddress(new IPV4Util(nextLine[0],nextLine[1]).getNetworkAddress());
            });
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, "Erro ao tentar ler o arquivo de endereços virtuais", ex);
        }
    }
}
