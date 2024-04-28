package model.player;

import java.util.ArrayList;

import model.card.Card;
import model.observer.AbstractListenableModel;

public class Hand extends AbstractListenableModel {
    /** The size of the player's hand */
    private int handSize;
    /** Players cards */
    private ArrayList<Card> handCards;

    /**
     * Constructor
     * @param handSize
     */
    public Hand(int handSize) {
        this.handSize = handSize;
        this.handCards = new ArrayList<>(handSize);
    }

    /**
     * Getter on the list of the player's cards
     * @return handCards ArrayList<Card>
     */
    public ArrayList<Card> getHandCards() { 
        return this.handCards;
    }

    /**
     * Getter on the hand size
     * @return handSize An integer
     */
    public int getHandSize() {
        return this.handSize;
    }

    /**
     * Add a card to the hand
     * @param c Card to add
     */
    public void addCard(Card c){
        this.handCards.add(c);
        super.fireChange();
    }

    /**
     * Remove a card from the hand
     * @param c Card to remove
     */
    public void removeCard(Card c){
        this.handCards.remove(c);
        //super.fireChange();
    }

    @Override
    public String toString() {
        return "Hand : "+ this.handCards ;
    }
    
}
