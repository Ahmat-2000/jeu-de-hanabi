package view;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

import model.Card;
import model.Game;
import model.DiscardedCards;
import model.observer.ModelListener;

/**
 * DiscardView est un JPanel personnalisé qui affiche les cartes défaussées.
 */
public class DiscardView extends JPanel implements ModelListener {
    
    /**
     * Les cartes défaussées.
     */
    private DiscardedCards discardedCards;

    /**
     * Constructeur pour créer un DiscardView avec le modèle de jeu spécifié.
     *
     * @param game Le modèle de jeu.
     */
    public DiscardView(Game game) {
        this.discardedCards = game.getDiscard();
        this.discardedCards.addModelListener(this);
        setPreferredSize(new Dimension(500, 80));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int cardWidth = 50; 
        int cardHeight = 50; 
        int gap = 10; 
        int panelWidth = this.getWidth();

        int x = gap;
        int y = gap + 10;

        for (Card card : this.discardedCards.getcardBag()) {
            card.setIsvisible(true);
            g.drawImage(new CardView(card).getCardImage(), x, y, null);
            x += cardWidth + gap;
            if (x + cardWidth + gap > panelWidth) {
                x = gap;
                y += cardHeight + gap;
            }
        }
    }

    @Override
    /**
     * Méthode appelée lorsque le modèle change.
     * Réinitialise et repeint le composant pour refléter les modifications.
     *
     * @param arg0 La source du changement.
     */
    public void somethinHasChanged(Object arg0) {
        this.revalidate();
        this.repaint();
    }
}
