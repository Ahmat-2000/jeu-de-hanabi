package model.player;

import model.Deck;
import model.card.Card;

public abstract class Player {
    private Hand hand;
    private int playerID;
    private Deck deck;

    public Player(int handSize,Deck deck,int id ){
        this.hand = new Hand(handSize);
        this.playerID = id;
        this.deck = deck;
        this.prepareHand(handSize);
    }
    private void prepareHand(int handSize) {
        for (int i = 0; i < handSize; i++) {
            this.hand.addCard(deck.peekCard());
        }
    }
    /**
     * A getter for the player's id
     * @return playerID The player's id
     */
    public int getPlayerID() {
        return playerID;
    }
    /**
     * A getter for the player's hand
     * @return hand The player's hand
     */
    public Hand getHand() {
        return hand;
    } 
    /**
     * Play a card
     * @param c The card to play
     */
    public void playCard(Card c){
        hand.removeCard(c);
        hand.addCard(deck.peekCard());
    }
    /**
     * Discard a card
     * @param c The card to discard
     */
    public void discard(Card c){
        this.playCard(c);
    }
    /**
     * Play a card by it index in the player's hand
     * @param index The index of the card
     * @return
     */
    public Card playCardByIndex(int index){
        Card c = hand.getHandCards().get(index);
        this.playCard(c);
        return c;
    }
    /**
     * Discard a card by it index in the player's hand
     * @param index The index of the card
     * @return
     */
    public Card discardByIndex(int index){
        return this.playCardByIndex(index);
    }
    // public abstract String giveHint();
    // public abstract int chooseAction();
    public abstract String playerType();
}
