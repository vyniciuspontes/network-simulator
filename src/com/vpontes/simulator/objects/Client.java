/*
*
* Simulador De Redes
* Redes I - Universidade Federal Fluminense
*
 */
package com.vpontes.simulator.objects;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
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

    public Client(List<Node> nodes) {
        this.nodes = nodes;
    }

    private void readNodeFile() throws FileNotFoundException, IOException {

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
