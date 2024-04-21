package model;

import java.util.Stack;

import model.card.Card;
import model.card.CardEnumColor;
import model.card.CardEnumValue;
import model.observer.AbstractListenableModel;

public class Deck extends AbstractListenableModel{
    private int size;
    private Stack<Card> deckStack;
    /**
     * 
     * @param size
     */
    public Deck(int size) {
        super();
        this.size = size;
        this.deckStack = new Stack<>();
        this.prepareDeck();
    }
    /**
     * 
     * @return
     */
    public int getSize() {
        return size;
    }
    /**
     * 
     * @return
     */
    public Stack<Card> getDeckStack() {
        return deckStack;
    }
    /**
     * 
     * @return
     */
    public Card peekCard(){
        Card c = deckStack.pop();
        super.fireChange();
        this.size = deckStack.size();
        return c;
    }
    public void prepareDeck(){
        // TODO
        for (int index = 0; index < size; index++) {
            this.deckStack.push(new Card(CardEnumColor.GREEN, CardEnumValue.ONE));
        }
        this.size = this.deckStack.size();
    }
    public void cleanDeck(){
        this.deckStack.clear();
        this.size = 0;
        super.fireChange();
    }

    @Override
    public String toString() {
        String tmp = "Deck : { ";
        for (Card card : deckStack) {
            tmp += card + ", ";
        }
        tmp += " }";
        return tmp;
    }
}
