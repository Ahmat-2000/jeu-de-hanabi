package model;

import java.util.ArrayList;

import model.card.Card;
import model.card.CardEnumColor;
import model.player.HumanPlayer;
import model.player.Player;

public class Game {
    public static final int minPlayer = 2;
    public static final int maxPlayer = 5;
    private Token blueToken;
    private Token redToken;
    private int nbPlayers;
    private Deck deck;
    private PlayedCards playedCards;
    private ArrayList<Player> players;
    private Player currentPlayer;
    private Board board;

    public Game(int nbPlayers) {
        this.nbPlayers = nbPlayers;
        this.prepareGame();
        this.preparePlayers();
        //TODO
        this.currentPlayer = this.players.get(0);
    }
    public void prepareGame(){
        this.blueToken = new Token("Blue", 8);
        this.redToken = new Token("Red" , 3);
        this.playedCards = new PlayedCards();
        this.players = new ArrayList<>();
        this.board = new Board(5);
        this.deck = new Deck(50);
    }
    public void preparePlayers(){
        //TODO
        int handSize = nbPlayers > 3 ? 4 : 5 ;
        for (int i = 0; i < nbPlayers; i++) {
            this.players.add(new HumanPlayer(handSize,this.deck,i));
        }
    }
    public void endGame(){
        this.board.cleanBoard();
        this.playedCards.clean();
        this.players.clear();
        this.deck.cleanDeck();
    }
    public Player getNextPlayer(){
        int index = this.players.indexOf(this.currentPlayer);
        return this.players.get((index + 1) % nbPlayers);
    }
    public Player getCurrentPlayer() {
        return this.currentPlayer;
    }

    public void setCurrentPlayer() {
        int index = this.players.indexOf(this.currentPlayer);
        this.currentPlayer = this.players.get((index + 1) % nbPlayers);
    }
    public boolean canPlayCard(){
        
        return true;
    }
    public boolean canNotDiscard(){
        return this.blueToken.isFullToken();
    }
    public boolean canNotGiveHint(){
        return this.blueToken.isNoTokenLeft();
    }
    public String giveHintByValue(int val, String message){
        for (int i = 0; i < this.currentPlayer.getHand().getHandSize() ; i++) {
            if (this.getNextPlayer().getHand().getHandCards().get(i).getCardEnumValue().getValue() == val) {
                message += (i+1)+", ";
            }
        }
        return message;
    }
    public String giveHintByColor(CardEnumColor color, String message){
        for (int i = 0; i < this.currentPlayer.getHand().getHandSize() ; i++) {
            if (this.getNextPlayer().getHand().getHandCards().get(i).getColor() == color) {
                message += (i+1)+", ";
            }
        }
        return message;
    }
    public void playCardByIndex(int index){
        Card c = this.currentPlayer.playCardByIndex(index);
        if (this.board.canAddCard(c)) {
            if (this.board.addToTheBoard(c) && !this.redToken.isFullToken()) {
                this.redToken.addToken();
            }
        }else{
            this.playedCards.addCard(c);
            this.redToken.removeToken();
        }
    }
    public void discardByIndex(int index){
        if (this.blueToken.isFullToken()) {
            throw new IllegalStateException("All INDICE tokens are on the bag, please perform another action.");
        }
        else{
            Card c = this.currentPlayer.discardByIndex(index);
            this.playedCards.addCard(c);
            this.blueToken.addToken();
        }
    }
    public int getScore(){
        return this.board.countScore();
    }
    public boolean isGameOver(){
        if (deck.getSize() == 0) {
            return true;
        }
        if (redToken.isNoTokenLeft()) {
            return true;
        }
        if (board.isCompleteFireworks()) {
            return true;
        }
        return false;
    }
    public Token getBlueToken() {
        return blueToken;
    }

    public Token getRedToken() {
        return redToken;
    }

    public int getNbPlayers() {
        return nbPlayers;
    }

    public Deck getDeck() {
        return deck;
    }

    public PlayedCards getPlayedCards() {
        return playedCards;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public Board getBoard() {
        return board;
    }

    //TODO
    public String getScoreFeedback() {
        int score = this.getScore();
        String scoreMessage = "Your score is " + score +".\n"; 
        if (score >= 25) {
            scoreMessage += "Perfect! A perfect score, well played!";
        } else if (score >= 20) {
            scoreMessage += "Impressive! You have excelled in this game.";
        } else if (score >= 15) {
            scoreMessage += "Well done! This is a good result.";
        } else if (score >= 10) {
            scoreMessage += "Not bad, but you can do better.";
        } else {
            scoreMessage += "Very very low";
        }
        return scoreMessage;
    }    
}
