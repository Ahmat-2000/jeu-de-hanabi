package model;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import model.card.Card;
import model.card.CardEnumColor;
import model.card.CardEnumValue;
import model.observer.AbstractListenableModel;

/**
 * This class represente a fireworks for the Hanabi game
 * It contains a Map that maps each of 5 colors to a stack of card
 */
public class Board extends AbstractListenableModel{

    /**
     * Stack maximum size
     */
    private int stackSize;
    /**
     * A map of stack of Card
     */
    private HashMap<CardEnumColor , Stack<Card>> fireworks;

    /**
     * Board constructor
     * @param stackSize max size of each stack
     */
    public Board(int stackSize) {
        super();
        this.stackSize = stackSize;
        this.fireworks = new HashMap<CardEnumColor , Stack<Card>>();
        this.prepareBoard();
    }

    /**
     * Prepare the board stack for new game
     */
    private void prepareBoard(){
        for (CardEnumColor color : CardEnumColor.values()) {
            this.fireworks.put(color, new Stack<>());
        }
    }
    /**
     * Clean the board stack for new game
     */
    public void cleanBoard(){
        for (CardEnumColor color : CardEnumColor.values()) {
            this.fireworks.get(color).clear();
        }
        super.fireChange();
    }
    /**
     * Check if we can add a new card to the board
     * @param card Card to add 
     * @return true if we can add this card to the board, otherwise false
     */
    public boolean canAddCard(Card card){
        Stack<Card> tmp = this.fireworks.get(card.getColor());
        if(tmp.size() == this.stackSize ){
            return false;
        }
        if(!tmp.empty() && card.getCardEnumValue().getValue() + 1 != tmp.peek().getCardEnumValue().getValue()){
            return false;
        }
        if(tmp.empty() && card.getCardEnumValue().getValue() != CardEnumValue.ONE.getValue()){
            return false;
        }
        return true;
    }
    
    /**
     * Add a new card to the board
     * @param card Card to add
     */
    public boolean addToTheBoard(Card card){
        this.fireworks.get(card.getColor()).add(card);
        super.fireChange();
        return this.fireworks.get(card.getColor()).size() == this.stackSize;
    }

    /**
     * Check if the fireworks is complete 
     * @return true if the fireworks is complete, otherwise false
     */
    public boolean isCompleteFireworks(){
        for(Map.Entry< CardEnumColor,Stack<Card> > entry : this.fireworks.entrySet()) {
            if (entry.getValue().size() != stackSize) {
                return false;
            }
        }
        return true;
    }
    public int countScore(){
        int score = 0;
        for(Map.Entry< CardEnumColor,Stack<Card> > entry : this.fireworks.entrySet()) {
            score += entry.getValue().size();
        }
        return score;
    }
    @Override
    public String toString() {
        String tmp = "Board : \n";
        for(Map.Entry< CardEnumColor,Stack<Card> > entry : this.fireworks.entrySet()) {
            tmp += "\t" + entry.getKey() + " : "+ entry.getValue().toString() + "\n";
        }
        tmp += "";
        return tmp;
    }
    
    
}