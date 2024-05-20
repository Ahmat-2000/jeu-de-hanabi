package view;

import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;

import model.Card;
import model.Deck;
import model.observer.ModelListener;

/**
 * DeckView est un JPanel personnalisé qui affiche le paquet de cartes.
 */
public class DeckView extends JPanel implements ModelListener {
    /**
     * Le paquet de cartes.
     */
    private Deck deck;

    /**
     * Constructeur pour créer un DeckView avec le paquet de cartes spécifié.
     *
     * @param deck Le paquet de cartes.
     */
    public DeckView(Deck deck) {
        this.deck = deck;
        this.deck.addModelListener(this);
        setPreferredSize(new Dimension(200, 0));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int i = 0;
        for (Card card : this.deck.getDeck()) {
            g.drawImage(new CardView(card).getCardImage(), 5 + i, 5 + i, null);
            i++;
        }
    }

    @Override
    /**
     * Méthode appelée lorsque le modèle change.
     * Réinitialise et repeint le composant pour refléter les modifications.
     *
     * @param arg0 La source du changement.
     */
    public void somethinHasChanged(Object source) {
        this.revalidate();
        this.repaint();
    }
}
