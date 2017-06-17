/*
*
* Simulador De Redes
* Redes I - Universidade Federal Fluminense
*
 */
package com.vpontes.simulator.objects;

import com.vpontes.simulator.objects.client.Client;
import com.vpontes.simulator.objects.client.MessageDispatcher;
import com.vpontes.simulator.utils.IPV4Util;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Vynicius
 */
public class Router {

    private List<RoutingRule> routingRules;
    private final String ROUTING_RULES_FILE_LOCATION = "routing-rules.txt";
    private MessageDispatcher dispatcher;
    private List<Node> openConnections;

    private Router() {

        this.readRoutingRulesFile();
        this.dispatcher = new MessageDispatcher();
        openConnections = new ArrayList<>();
    }

    private static Router instance;

    static public Router getInstance() {
        if (instance == null) {
            instance = new Router();
        }
        return instance;
    }

    public Node getOriginNode(String destinationAddress) throws IllegalArgumentException {

        Node currentNode = null;

        for (RoutingRule routingRule : routingRules) {

            if (!routingRule.getSubnetAddress().equals("0.0.0.0") && !routingRule.getSubnetMask().equals("0.0.0.0")) {
                IPV4Util util = new IPV4Util(routingRule.getSubnetAddress(), routingRule.getSubnetMask());

                if (util.contains(destinationAddress)) {
                    return NodesCatalog.getInstace().getNode(routingRule.getIndex());
                }
            } else {
                currentNode = NodesCatalog.getInstace().getNode(routingRule.getIndex());
            }
        }
        
        if (currentNode == null) {
            throw new IllegalArgumentException("Interface de origem não encontrada com os valores da tabela de roteamento");
        }

        return currentNode;
    }

    public void route(IPV4Datagram datagram) throws IOException, IllegalArgumentException{

        if(datagram.getTtl() == 0){
            throw new IllegalArgumentException("TTL igual a 0");
        }
        Node currentNode = null;

        for (RoutingRule routingRule : routingRules) {
            if (!routingRule.getSubnetAddress().equals("0.0.0.0") && !routingRule.getSubnetMask().equals("0.0.0.0")) {
                IPV4Util util = new IPV4Util(routingRule.getSubnetAddress(), routingRule.getSubnetMask());

                if (util.contains(datagram.getDestinationIPAddress())) {
                    currentNode = NodesCatalog.getInstace().getNode(routingRule.getIndex());
                    break;
                }
            } else {
                currentNode = NodesCatalog.getInstace().getNode(routingRule.getIndex());
            }
        }

        if (currentNode == null) {
            throw new IllegalArgumentException("Endereço não encontrado na tabela de roteamento");
        }
        System.out.println("Encaminhando datagrama para endereco virtual: " + currentNode.getVirtualAddress());
        
        if(openConnections.isEmpty() || !isConnectionOpen(currentNode.getAddress(), currentNode.getDoor())){
            dispatcher.startConnection(currentNode.getAddress(), currentNode.getDoor());
            openConnections.add(currentNode);
        }
        datagram.decreaseTTL(1);
        dispatcher.sendMessage(datagram);
    }
    
    private boolean isConnectionOpen(String address, Integer door){
        
        return openConnections.stream().anyMatch(c -> c.getAddress().equals(address) && c.getDoor().equals(door));
    }

    private void readRoutingRulesFile() {
        try {
            this.routingRules = new ArrayList<>();

            System.out.println("Lendo o arquivo: " + ROUTING_RULES_FILE_LOCATION);

            Scanner scanner = new Scanner(new File(ROUTING_RULES_FILE_LOCATION));
            while (scanner.hasNextLine()) {
                String[] nextLine = scanner.nextLine().split(" ");

                Node validNode = NodesCatalog.getInstace().getNode(Integer.valueOf(nextLine[2]));

                if (!nextLine[0].equals("0.0.0.0") && !nextLine[1].equals("0.0.0.0")) {
                    boolean validNetmask = IPV4Util.verifyNetmask(nextLine[1]);

                    if (!validNetmask) {
                        throw new IllegalArgumentException("Mascara de rede invalida: " + nextLine[1]);
                    }
                }

                if (validNode == null) {
                    throw new IllegalArgumentException("Index da interface não encontrada: " + nextLine[2]);
                }

                RoutingRule rule = new RoutingRule(nextLine[0], nextLine[1], Integer.valueOf(nextLine[2]));
                this.routingRules.add(rule);
            }

        } catch (FileNotFoundException | IllegalArgumentException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, "Erro ao tentar ler o arquivo de roteamento", ex);
        }
    }
}
