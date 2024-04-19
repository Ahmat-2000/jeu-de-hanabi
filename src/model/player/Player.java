package model.player;

import model.card.Card;

public abstract class Player {
    private Hand hand;

    public Player(Hand h){
        hand = h;
    }
    public Hand getHand() {
        return hand;
    }
    public void discardByIndex(int index){
        discard(hand.getHainCards().get(index));
    }

    public abstract void playCard(Card c);
    public abstract void discard(Card c);
}
