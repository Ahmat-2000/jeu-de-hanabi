package model;

import java.util.ArrayList;

import model.card.Card;
import model.player.Player;

public class Game {
    public static final int minPlayer = 2;
    public static final int maxPlayer = 5;
    private int score;
    private Deck deck;
    private PlayedCards playedCards;
    private ArrayList<Player> players;
    private Player currentPlayer;
    private Board board;

    public Game(Deck deck) {
        this.deck = deck;
        this.playedCards = new PlayedCards();
        this.players = new ArrayList<>();
        this.board = new Board(5);
        this.preparePlayers();
        //TODO
        this.currentPlayer = this.players.get(0);
    }

    public void preparePlayers(){
        // TODO
    }
    
}
