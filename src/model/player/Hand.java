package model.player;

import java.util.ArrayList;

import model.Deck;
import model.card.Card;
import model.observer.AbstractListenableModel;

public class Hand extends AbstractListenableModel {
    private int handSize;
    private ArrayList<Card> handCards;

    private Deck deck;
    public Hand(int handSize,Deck deck) {
        this.handSize = handSize;
        this.handCards = new ArrayList<>(handSize);
        this.deck = deck;
        this.prepareHand();
    }
    public ArrayList<Card> getHainCards() { 
        return handCards;
    }
    public int getHandSize() {
        return handSize;
    }
    private void prepareHand() {
        for (int i = 0; i < handSize; i++) {
            handCards.add(deck.peekCard());
        }
    }
    public void addCard(Card c){
        handCards.add(c);
        fireChange();
    }
    public void removeCard(Card c){
        handCards.remove(c);
        fireChange();
    }
}
