package model;

import java.util.Collections;
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
    /**
     * Push 50 cards to the deck, 10 cards for each color.
     * for each color add 1,1,1,2,2,3,3,4,4,5
     */
    public void prepareDeck(){
        for (CardEnumColor color : CardEnumColor.values()) {
            for (int i = 0; i < 3; i++) {
                this.deckStack.push(new Card(color, CardEnumValue.ONE));
                if(i != 0){
                    this.deckStack.push(new Card(color, CardEnumValue.TWO));
                    this.deckStack.push(new Card(color, CardEnumValue.THREE));
                    this.deckStack.push(new Card(color, CardEnumValue.FOUR));
                }
            }
            this.deckStack.push(new Card(color, CardEnumValue.FIVE));
        }
        Collections.shuffle(deckStack);
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
        tmp += "}";
        return tmp;
    }
}
