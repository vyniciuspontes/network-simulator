/*
*
* Simulador De Redes
* Redes I - Universidade Federal Fluminense
*
 */
package com.vpontes.simulator.ui;

import com.vpontes.simulator.objects.Simulator;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

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

        this.initializeServerMenu();
        this.readConnectionOptions();
    }

    private void initializeServerMenu() {

        int finish = 0;
        do {
            try {
                System.out.println("Digite o nome do server");
                String name = this.scanner.next();
                System.out.println("Digite o número de porta do server");
                Integer door = this.scanner.nextInt();
                this.simulator.createServer(door, name);
                finish = 1;
            } catch (IOException | InterruptedException | InputMismatchException | IllegalArgumentException ex) {

                System.out.println("Erro ao criar servidor: " + ex.toString());
            }
        } while (finish != 1);

        //this.simulator.createServer(8050, "A");
    }

    private void readConnectionOptions() {
        int stop = 0;

        
        do {
            try {
                System.out.println("Digite um endereço virtual para iniciar uma conexão");
                this.simulator.startConnection(scanner.next());
                scanner.nextLine();
                stop = 1;
            } catch (Exception ex) {
                System.out.println("Erro ao iniciar conexão: " + ex.toString());
            }
        } while (stop != 1);

        System.out.println("Digite uma mensagem !");
        
        do {
            try {
                String message = scanner.nextLine();
                this.simulator.sendMessage(message);
            } catch (Exception ex) {
                System.out.println("Erro ao enviar mensagem: " + ex.toString());
            }
        } while (true);

    }
}
