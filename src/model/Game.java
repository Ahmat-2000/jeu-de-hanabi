package model;

import java.util.ArrayList;

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

    public Game(Deck deck, int nbPlayers) {
        this.nbPlayers = nbPlayers;
        this.blueTocken = new Tocken("Blue", 8);
        this.redTocken = new Tocken("Red" , 3);
        this.deck = deck;
        this.playedCards = new PlayedCards();
        this.players = new ArrayList<>();
        this.board = new Board(5);

        this.preparePlayers();
        //TODO
        this.currentPlayer = this.players.get(0);
    }

    public void preparePlayers(){
        //TODO
        int handSize = nbPlayers > 3 ? 4 : 5 ;
        for (int i = 0; i < nbPlayers; i++) {
            this.players.add(new HumanPlayer(handSize,this.deck,i));
        }
    }
    public Player getCurrentPlayer() {
        return this.currentPlayer;
    }

    public void setCurrentPlayer() {
        int index = this.players.indexOf(this.currentPlayer);
        this.currentPlayer = this.players.get((index + 1) % nbPlayers);
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
}
