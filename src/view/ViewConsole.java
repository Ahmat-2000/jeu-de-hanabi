package view;

import java.util.InputMismatchException;
import java.util.Scanner;
import model.Game;
import model.card.CardEnumColor;

/**
 * Classe ViewConsole pour gérer l'interface utilisateur de console pour le jeu de Hanabi.
 * Fournit des méthodes pour afficher le jeu, choisir des actions, et interagir avec l'utilisateur.
 */
public class ViewConsole {
    private Game game;
    private Scanner scanner;

    /**
     * Constructeur de la classe ViewConsole.
     * @param game L'instance de Game qui représente l'état actuel du jeu.
     */
    public ViewConsole(Game game) {
        this.game = game;
        scanner = new Scanner(System.in);
    }

    /**
     * Affiche l'état actuel du jeu.
     */
    public void printGame(){
        System.out.println("############################################");
        System.out.println("It's " + game.getCurrentPlayer() + " turn");
        System.out.println(game.getBoard());
        System.out.println("Opponent " + game.getNextPlayer().getHand());
        System.out.println(game.getPlayedCards());
        System.out.println(game.getBlueToken());
        System.out.println(game.getRedToken());
        System.out.println("Deck size : " + game.getDeck().getSize() + "\n");
    }

    /**
     * Affiche les actions possibles dans le jeu.
     */
    public void printAction(){
        System.out.println("Game Actions : ");
        System.out.println("\t 1 - Play a card");
        System.out.println("\t 2 - Discard a card");
        System.out.println("\t 3 - Give hint to your opponent\n");
    }

    /**
     * Affiche les types d'indices que l'utilisateur peut donner.
     */
    public void printHint(){
        System.out.println("Game Hints : ");
        System.out.println("\t 1 - Give hint on the color");
        System.out.println("\t 2 - Give hint on the value\n");
    }

    /**
     * Permet à l'utilisateur de choisir une carte de sa main.
     * @param message Le message à afficher à l'utilisateur.
     * @return L'indice de la carte choisie.
     */
    public int chooseCard(String message){
        int size = game.getCurrentPlayer().getHand().getHandSize();
        return promptForNumber(message + " please choose a card index between 1 and " + size + " : ", 1, size) - 1;
    }

    /**
     * Traite l'action de jouer une carte.
     */
    public void printPlayCard(){
        this.game.playCardByIndex(this.chooseCard("To play a card,"));
    }

    /**
     * Traite l'action de jeter une carte.
     */
    private void printDiscard() {
        this.game.discardByIndex(this.chooseCard("To discard a card,"));
    }

    /**
     * Traite l'action de donner un indice.
     */
    private void printGiveHint() {
        this.printHint();
        int index = promptForNumber("\nPlease choose a hint (1 for color, 2 for value): ", 1, 2);
        if (index == 1) {
            System.out.println("\nChoose a color number from the following:");
            CardEnumColor[] colors = CardEnumColor.values();
            for (int i = 0; i < colors.length; i++) {
                System.out.println("\t" + (i + 1) + ". " + colors[i]);
            }
            int colorChoice = promptForNumber("Please choose a valid color number between 1 and " + colors.length + ": ", 1, colors.length);
            String hintMessage = "Hint for color: ";
            try {
                hintMessage = game.giveHintByColor(colors[colorChoice-1], hintMessage);
                System.out.println(hintMessage);
            } catch (Exception e) {
                System.out.println("There is no blue token left");
            }
        } else if (index == 2) {
            int numberChoice = promptForNumber("\nChoose a number between 1 and 5: ", 1, 5);
            String hintMessage = "Hint for value: ";
            try {
                hintMessage = game.giveHintByValue(numberChoice, hintMessage);
                System.out.println(hintMessage);
            } catch (Exception e) {
                System.out.println("There is no blue token left");
            }
        }
    }

    /**
     * Méthode générique pour demander à l'utilisateur un nombre dans une plage donnée.
     * @param message Le message à afficher.
     * @param min La valeur minimale.
     * @param max La valeur maximale.
     * @return Le nombre entré par l'utilisateur.
     */
    private int promptForNumber(String message, int min, int max) {
        while (true) {
            System.out.print(message);
            try {
                int number = scanner.nextInt();
                if (number >= min && number <= max) {
                    return number;
                }
                System.out.println("Please enter a number between " + min + " and " + max + ".");
            } catch (InputMismatchException e) {
                scanner.next(); // nettoie l'entrée incorrecte
                System.out.println("Please enter a valid number.");
            }
        }
    }

    /**
     * Exécute une action choisie par l'utilisateur.
     * @param action Le numéro de l'action à exécuter.
     */
    public void executeAction(int action){
        switch (action) {
            case 1:
                this.printPlayCard();
                break;
            case 2:
                this.printDiscard();
                break;
            case 3:
                this.printGiveHint();
                break;
            default:
                System.out.println("Invalid action !!");
                break;
        }
    }

    /**
     * Permet à l'utilisateur de choisir et d'exécuter une action.
     */
    public void chooseAction(){
        if(game.getCurrentPlayer().playerType().equals("AI")){
            //TODO AI Logic
        }else{
            while (true) {
                this.printAction();
                int action = promptForNumber("Please choose action number between 1, 2 and 3 : ", 1, 3);
                if ((action == 2 && this.game.canNotDiscard()) || (action == 3 && this.game.canNotGiveHint())) {
                    System.out.println("Unable to perform the selected action, please choose another.\n");
                    continue;
                }
                this.executeAction(action);
                break;
            }
        }
    }

    /**
     * Démarre le jeu et gère le flux de jeu jusqu'à la fin.
     */
    public void startGame(){
        while (!this.game.isGameOver()) {
            this.printGame();
            this.chooseAction();
            this.game.setCurrentPlayer();
        }
        this.game.endGame();
        System.out.println(this.game.getScoreFeedback());
        this.scanner.close();
    }
}
