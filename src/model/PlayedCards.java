package model;

import java.util.ArrayList;

import model.card.Card;
import model.observer.AbstractListenableModel;

public class PlayedCards extends AbstractListenableModel{
    
    private ArrayList<Card> playeCards;

    public PlayedCards() {
        super();
        this.playeCards = new ArrayList<>();
    }

    public void addCard(Card card){
        this.playeCards.add(card);
        super.fireChange();
    }
    
    public void clean(){
        this.playeCards.clear();
        super.fireChange();
    }

    public ArrayList<Card> getPlayeCards() {
        return this.playeCards;
    }

    @Override
    public String toString() {
        return "PlayedCards : "+this.playeCards;
    }

    
}
