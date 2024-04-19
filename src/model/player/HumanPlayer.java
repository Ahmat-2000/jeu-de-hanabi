package model.player;

import model.card.Card;

public class HumanPlayer extends Player{

    public HumanPlayer(Hand h) {
        super(h);
        //TODO Auto-generated constructor stub
    }

    @Override
    public void playCard(Card c) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'playCard'");
    }

    @Override
    public void discard(Card c) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'discard'");
    }
    
}
