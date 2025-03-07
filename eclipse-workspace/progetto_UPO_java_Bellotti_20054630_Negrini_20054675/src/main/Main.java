package main;

import java.util.Scanner;

import interfaccia.cli.Cli;
import interfaccia.gui.Gui;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);

        int scelta = 0;

        do {
            System.out.println("\nBenvenuto! Selezionare l'interfaccia da utilizzare:");
            System.out.println("1. Linea di comando");
            System.out.println("2. Interfaccia grafica");
            System.out.println("9. Esci");
            System.out.print("Scelta: ");
                   
            scelta = scanner.nextInt();
            
            switch (scelta) {
                case 1:
                    System.out.println("Selezionata linea di comando.");
                    new Cli();
                    break;
                case 2:
                    System.out.println("Selezionata interfaccia grafica.");
                    new Gui();
                    break;
                case 9:
                    System.out.println("Chiusura programma.");
                    break;
                default:
                    System.out.println("Scelta non valida. Riprovare.");
            }
        } while (scelta != 9);

        scanner.close();
    }
}
