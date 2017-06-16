/*
*
* Simulador De Redes
* Redes I - Universidade Federal Fluminense
*
 */
package com.vpontes.simulator.ui;

import com.vpontes.simulator.objects.IPV4Datagram;
import com.vpontes.simulator.objects.Simulator;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author aline
 */
public class Program {

    public static void main(String[] args) {
        new Program().run();
    }
    
    private Simulator simulator;
    private Scanner scanner;

    public Program() {
        this.simulator = new Simulator();
        this.scanner = new Scanner(System.in);
    }
        
    public void run() {

        try {
            this.initializeServerMenu();
            this.simulator.startConnection("10.0.0.1");
            this.simulator.sendMessage("Vynicius Morais Pontes ama muito Aline Ribeiro Torres");
            this.simulator.sendMessage("Quer casar cmg ?");
            Integer menuOption = this.readMenuOptions();
            
            switch (menuOption){
                case 1: break;
                case 2: System.exit(0); break;
            }
            
        } catch (IOException | InterruptedException ex) {
            Logger.getLogger(Simulator.class.getName()).log(Level.SEVERE, ex.getMessage());
        }
    }

    private void initializeServerMenu() throws IOException, InterruptedException {
        System.out.println("Digite o nome do server");
        String name = this.scanner.next();
        System.out.println("Digite o número de porta do server");
        Integer door = this.scanner.nextInt();
        this.simulator.createServer(door, name);

        //this.simulator.createServer(8050, "A");
    }
    
    private Integer readMenuOptions() {

        int option = 0;

        do {
            System.out.println();
            System.out.println("Digite 1 para iniciar uma conexão");
            System.out.println("Digite 2 para finalizar");

            option = scanner.nextInt();

        } while (option == 0 && (option == 1 || option == 2));

        return option;
    }
}
