package view;

import java.util.Scanner;

import model.Game;
import model.card.CardEnumColor;

public class ViewConsole {
    private Game game;
    private Scanner scanner;
    public ViewConsole(Game game) {
        this.game = game;
        scanner = new Scanner(System.in);

    }

    public void printGame(){
        System.out.println("############################################");
        System.out.println("It's "+game.getCurrentPlayer()+" turn");
        System.out.println(game.getBoard());
        System.out.println("Opponent " + game.getNextPlayer().getHand());
        System.out.println(game.getPlayedCards());
        System.out.println(game.getBlueToken());
        System.out.println(game.getRedToken());
        System.out.println("Deck size : " + game.getDeck().getSize()+"\n");
        // System.out.println(game.getDeck());

    }
    public void printAction(){
        System.out.println("Game Actions : ");
        System.out.println("\t 1 - Play a card");
        System.out.println("\t 2 - Discard a card");
        System.out.println("\t 3 - Give hint to your opponent\n");
    }

    public void printHint(){
        System.out.println("Game Hints : ");
        System.out.println("\t 1 - Give hint on the color");
        System.out.println("\t 2 - Give hint on the value\n");
    }
    public int chooseCard(String message){
        int size = game.getCurrentPlayer().getHand().getHandSize();
        System.out.print(message + " please choose a card index between 1 and " + size + " : ");
        int index = scanner.nextInt() - 1;
        System.out.println();
        while (index < 0 || index > size -1) {
            System.out.print(message + " please choose a card index between 1 and " + size + " : ");
            index = scanner.nextInt() -1;
            System.out.println();
        }
        return index;
    }
    public void printPlayCard(){
        this.game.playCardByIndex(this.chooseCard("To play a card,"));
    }
    private void printDiscard() {
        this.game.discardByIndex(this.chooseCard("To discard a card,"));
    }    
        
    private void printGiveHint() {
        this.printHint();
        System.out.print("\nPlease choose a hint (1 for color, 2 for value): ");
        int index = scanner.nextInt();
        while (index < 1 || index > 2) {
            System.out.print("\nPlease choose a valid hint number (1 or 2): ");
            index = scanner.nextInt();
        }
        if (index == 1) {
            System.out.println("\nChoose a color number from the following: ");
            CardEnumColor[] colors = CardEnumColor.values();
            String showColor = "";
            for (int i = 0; i < colors.length; i++) {
                showColor += "\t" + (i + 1) + ". " + colors[i] + "\n";
            }
            System.out.println(showColor);
            int colorChoice = scanner.nextInt();
            while (colorChoice < 1 || colorChoice > colors.length) {
                System.out.println("Please choose a valid color number between 1 and " + colors.length + ": ");
                colorChoice = scanner.nextInt();
            }
            // Générer l'indice et l'afficher
            String hintMessage = "Hint for color: ";
            hintMessage = game.giveHintByColor(colors[colorChoice-1], hintMessage);
            System.out.println(hintMessage);
        } else if (index == 2) {
            System.out.println("\nChoose a number between 1 and "+ game.getCurrentPlayer().getHand().getHandSize() +": ");
            int numberChoice = scanner.nextInt();
            while (numberChoice < 1 || numberChoice > game.getCurrentPlayer().getHand().getHandSize()) {
                System.out.println("Please choose a valid number between 1 and "+ game.getCurrentPlayer().getHand().getHandSize() +": ");
                numberChoice = scanner.nextInt();
            }
            // Générer l'indice et l'afficher
            String hintMessage = "Hint for value: ";
            hintMessage = game.giveHintByValue(numberChoice, hintMessage);
            System.out.println(hintMessage);
        }
    }
    

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

    public void chooseAction(){
        int action = 0;
        if(game.getCurrentPlayer().playerType().equals("AI")){
            //TODO
        }else{
            while (true) {
                this.printAction();
            
                System.out.print("Please choose action number between 1, 2 and 3 : ");
                action = this.scanner.nextInt();
                while (action < 1 || action > 3) {
                    System.out.print("Please choose action number between 1, 2 and 3 : ");
                    action = this.scanner.nextInt();
                    System.out.println();
                }
                if (action == 2 && this.game.canNotDiscard()) {
                    System.out.println("All Blues tokens are on the bag, please perform another action.\n");
                    continue;
                }
                else if (action == 3 && this.game.canNotGiveHint()) {
                    System.out.println("There is no Blues tokens left on the bag, please perform another action.\n");
                    continue;
                }
                else{
                    this.executeAction(action);
                    break;
                }
            }
        }
    }
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
