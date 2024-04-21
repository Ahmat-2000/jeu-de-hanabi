package model.player;

import model.Deck;

public class AdvancedBotPlayer extends Player{

    public AdvancedBotPlayer(int handSize,Deck deck, int id) {
        super(handSize,deck,id);
    }
    
    @Override
    public String toString() {
        return "AdvancedBotPlayer N°" + super.getPlayerID();
    }
}
