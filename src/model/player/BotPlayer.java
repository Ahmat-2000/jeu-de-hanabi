package model.player;

import model.Deck;

public class BotPlayer extends Player{

    public BotPlayer(int handSize,Deck deck, int id) {
        super(handSize,deck,id);
    }

    @Override
    public String toString() {
        return "BotPlayer N°" + (super.getPlayerID() + 1);
    }
    @Override
    public String playerType() {
        return "AI";
    }
}
