package model.player;

import model.Deck;

public class HumanPlayer extends Player{

    public HumanPlayer(int handSize,Deck deck, int id) {
        super(handSize,deck,id);
    }

    @Override
    public String toString() {
        return "HumanPlayer N°" + (super.getPlayerID() + 1);
    }

    @Override
    public String playerType() {
        return "Human";
    }
    
    
}
