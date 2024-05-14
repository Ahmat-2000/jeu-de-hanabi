package model;

import java.util.ArrayList;
import model.card.Card;
import model.card.CardEnumColor;
import model.player.HumanPlayer;
import model.player.Player;

/**
 * Represents the game state and logic for a game of Hanabi.
 * Manages players, cards, and game progression.
 */
public class Game {
    /** Minimum number of players. */
    public static final int MIN_PLAYER = 2;

    /** Maximum number of players. */
    public static final int MAX_PLAYER = 5;

    /** Token tracker for available hint tokens. */
    private Token blueToken;

    /** Token tracker for penalty tokens. */
    private Token redToken;

    /** Number of players in the game. */
    private int nbPlayers;

    /** Deck of cards for the game. */
    private Deck deck;

    /** Container for played cards. */
    private PlayedCards playedCards;

    /** List of players in the game. */
    private ArrayList<Player> players;

    /** Currently active player. */
    private Player currentPlayer;

    /** Game board managing the card stack. */
    private Board board;

    /**
     * Initializes a new game with the specified number of players.
     * @param nbPlayers the number of players in the game
     */
    public Game(int nbPlayers) {
        this.nbPlayers = nbPlayers;
        prepareGame();
        preparePlayers();
        currentPlayer = players.get(0);
    }

    /** Prepares the game components needed to start the game. */
    public void prepareGame() {
        blueToken = new Token("Blue", 8);
        redToken = new Token("Red", 3);
        playedCards = new PlayedCards();
        players = new ArrayList<>();
        board = new Board(5);
        deck = new Deck(50);
    }

    /** Initializes players for the game based on the number of participants. */
    public void preparePlayers() {
        int handSize = nbPlayers > 3 ? 4 : 5;
        for (int i = 0; i < nbPlayers; i++) {
            players.add(new HumanPlayer(handSize, deck, i));
        }
    }

    /** Cleans up game state components at the end of the game. */
    public void endGame() {
        board.cleanBoard();
        playedCards.clean();
        players.clear();
        deck.cleanDeck();
    }

    /**
     * Gets the next player in the turn sequence.
     * @return Player next in sequence
     */
    public Player getNextPlayer() {
        int index = players.indexOf(currentPlayer);
        return players.get((index + 1) % nbPlayers);
    }

    /** Returns the currently active player. */
    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    /** Sets the next player in the sequence as the current player. */
    public void setCurrentPlayer() {
        int index = players.indexOf(currentPlayer);
        currentPlayer = players.get((index + 1) % nbPlayers);
    }

    /** Returns whether a player can legally play a card. */
    public boolean canPlayCard() {
        //todo
        return true; // Placeholder for actual logic
    }

    /** Returns whether it is not possible to discard due to full blue tokens. */
    public boolean canNotDiscard() {
        return blueToken.isFullToken();
    }

    /** Returns whether it is not possible to give hints due to no blue tokens left. */
    public boolean canNotGiveHint() {
        return blueToken.isNoTokenLeft();
    }

    /** 
     * Provides a hint based on the value of the cards.
     * @param val The value to hint about.
     * @param message Initial message to append hint information.
     * @return Updated hint message.
     */
    public String giveHintByValue(int val, String message) throws Exception {
        if (this.blueToken.isNoTokenLeft()) {
            throw new Exception();
        }
        for (int i = 0; i < currentPlayer.getHand().getHandSize(); i++) {
            if (getNextPlayer().getHand().getHandCards().get(i).getCardEnumValue().getValue() == val) {
                message += (i + 1) + ", ";
            }
        }
        this.blueToken.removeToken();
        return message;
    }

    /** 
     * Provides a hint based on the color of the cards.
     * @param color The color to hint about.
     * @param message Initial message to append hint information.
     * @return Updated hint message.
     * @throws Exception 
     */
    public String giveHintByColor(CardEnumColor color, String message) throws Exception {
        if (this.blueToken.isNoTokenLeft()) {
            throw new Exception();
        }
        for (int i = 0; i < currentPlayer.getHand().getHandSize(); i++) {
            if (getNextPlayer().getHand().getHandCards().get(i).getColor() == color) {
                message += (i + 1) + ", ";
            }
        }
        this.blueToken.removeToken();
        return message;
    }

    /** Executes the action of playing a card by index. */
    public void playCardByIndex(int index) {
        Card c = currentPlayer.playCardByIndex(index);
        if (board.canAddCard(c)) {
            if (board.addToTheBoard(c) && !redToken.isFullToken()) {
                redToken.addToken();
            }
        } else {
            playedCards.addCard(c);
            redToken.removeToken();
        }
    }

    /** Executes the action of discarding a card by index. */
    public void discardByIndex(int index) {
        if (blueToken.isFullToken()) {
            throw new IllegalStateException("All INDICE tokens are on the bag, please perform another action.");
        } else {
            Card c = currentPlayer.discardByIndex(index);
            playedCards.addCard(c);
            blueToken.addToken();
        }
    }

    /** Calculates and returns the current game score. */
    public int getScore() {
        return board.countScore();
    }

    /** Determines if the game is over. */
    public boolean isGameOver() {
        if (deck.getSize() == 0 || redToken.isNoTokenLeft() || board.isCompleteFireworks()) {
            return true;
        }
        return false;
    }

    /** Returns the number of blue tokens left. */
    public Token getBlueToken() {
        return blueToken;
    }

    /** Returns the number of red tokens left. */
    public Token getRedToken() {
        return redToken;
    }

    /** Returns the number of players in the game. */
    public int getNbPlayers() {
        return nbPlayers;
    }

    /** Returns the current deck of cards. */
    public Deck getDeck() {
        return deck;
    }

    /** Returns the container for played cards. */
    public PlayedCards getPlayedCards() {
        return playedCards;
    }

    /** Returns the game board. */
    public Board getBoard() {
        return board;
    }

    /**
     * Generates feedback based on the final game score.
     * @return Score feedback string.
     */
    public String getScoreFeedback() {
        int score = getScore();
        String scoreMessage = "Your score is " + score + ".\n";
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
