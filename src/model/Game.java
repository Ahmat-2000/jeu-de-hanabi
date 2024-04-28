package model;

import java.util.ArrayList;

import model.card.Card;
import model.player.HumanPlayer;
import model.player.Player;

public class Game {
    public static final int minPlayer = 2;
    public static final int maxPlayer = 5;
    private Tocken blueTocken;
    private Tocken redTocken;
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
        this.blueTocken = new Tocken("Blue", 8);
        this.redTocken = new Tocken("Red" , 3);
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
    public void playCardByIndex(int index){
        Card c = this.currentPlayer.playCardByIndex(index);
        if (this.board.canAddCard(c)) {
            if (this.board.addToTheBoard(c) && !this.redTocken.isFullTocken()) {
                this.redTocken.addTocken();
            }
        }else{
            this.playedCards.addCard(c);
            this.redTocken.removeTocken();
        }
    }
    public int getScore(){
        return this.board.countScore();
    }
    public boolean isGameOver(){
        if (deck.getSize() == 0) {
            return true;
        }
        if (redTocken.isNoTockenLeft()) {
            return true;
        }
        if (board.isCompleteFireworks()) {
            return true;
        }
        return false;
    }
    public Tocken getBlueTocken() {
        return blueTocken;
    }

    public Tocken getRedTocken() {
        return redTocken;
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
