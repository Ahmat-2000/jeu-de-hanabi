package main;

import controller.GameControllerTerminal;

/**
 * Classe principale du projet Hannabi.
 * Cette classe initialise et lance le contrôleur de jeu en mode console.
 */
public class Hannabi {
    /**
     * Point d'entrée principal de l'application.
     *
     * @param args Les arguments de la ligne de commande.
     */
    public static void main(String[] args) {
        GameControllerTerminal controller = new GameControllerTerminal();

        controller.play();
        controller.close();
    }
}
