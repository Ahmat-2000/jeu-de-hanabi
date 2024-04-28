package view;

import java.util.Scanner;

import model.Game;

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
        System.out.println(game.getBlueTocken());
        System.out.println(game.getRedTocken());
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

    public void printPlayCard(){
        int size = game.getCurrentPlayer().getHand().getHandSize();
        System.out.print("To play a card, please choose a card index between 1 and " + size + " : ");
        int index = scanner.nextInt() - 1;
        System.out.println();
        while (index < 0 || index > size -1) {
            System.out.print("To play a card, please choose a card index between 1 and " + size + " : ");
            index = scanner.nextInt() -1;
            System.out.println();
        }
        this.game.playCardByIndex(index);
    }
    private void printDiscard() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'printDiscard'");
    }

    private void printGiveHint() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'printGiveHint'");
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

    public int chooseAction(){
        int action = 0;
        if(game.getCurrentPlayer().playerType().equals("AI")){
            //TODO
        }else{
            System.out.print("Please choose action number between 1, 2 and 3 : ");
            action = this.scanner.nextInt();
            while (action < 1 || action > 3) {
                System.out.print("Please choose action number between 1, 2 and 3 : ");
                action = this.scanner.nextInt();
                System.out.println();
            }
        }
        return action;
    }
    public void startGame(){
        int action = 0;
        while (!this.game.isGameOver()) {
            this.printGame();
            this.printAction();
            action = this.chooseAction();
            this.executeAction(action);
            this.game.setCurrentPlayer();
        }
        this.game.endGame();
        System.out.println(this.game.getScoreFeedback());
        this.scanner.close();

    }
}
