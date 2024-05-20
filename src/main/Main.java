package main;

import javax.swing.JOptionPane;

import controller.GameControllerTerminal;
import view.GUI;
import controller.HomeController;

/**
 * La classe Main est le point d'entrée de l'application.
 * Elle permet à l'utilisateur de choisir entre une interface graphique et une interface console.
 */
public class Main {
    /**
     * Le point d'entrée principal de l'application.
     *
     * @param args Les arguments de la ligne de commande.
     */
    public static void main(String[] args) {
        String[] options = {"Interface Graphique", "Console"};
        String choice = (String) JOptionPane.showInputDialog(
                null,
                "Choisissez une interface de jeu:",
                "Mode de Jeu",
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]);

        if (choice != null) {
            if (choice.equals("Interface Graphique")) {
                GUI window = new GUI();
                HomeController controller = new HomeController(window);
                controller.home();
                window.setVisible(true);
            } else if (choice.equals("Console")) {
                GameControllerTerminal controller = new GameControllerTerminal();
                controller.play();
                controller.close();
            }
        } else {
            System.out.println("Aucune sélection effectuée. Le programme va se terminer.");
        }
    }
}
