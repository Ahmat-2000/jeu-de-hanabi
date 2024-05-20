package view;

import javax.swing.JPanel;
import java.awt.*;
import model.Hand;
import model.Card;
import model.observer.ModelListener;

/**
 * HandView est un JPanel personnalisé qui affiche les cartes d'une main.
 */
public class HandView extends JPanel implements ModelListener {
    /**
     * La main à afficher.
     */
    private Hand hand;

    /**
     * Indique si les cartes sont visibles.
     */
    private boolean visibleCard = false;

    /**
     * Constructeur pour créer une vue de main avec la main spécifiée.
     *
     * @param hand La main à afficher.
     */
    public HandView(Hand hand) {
        this.hand = hand;
        this.hand.addModelListener(this);
        setPreferredSize(new Dimension(this.hand.getSize() == 5 ? 300 : 250, 80));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int numberOfCards = this.hand.getCards().size();
        int c = (numberOfCards * 50 + 5 * numberOfCards) / 2;
        c = 20;
        int i = 0;
        for (Card card : this.hand.getCards()) {
            if (visibleCard) {
                card.setIsvisible(true);
            }
            g.drawImage(new CardView(card).getCardImage(), c + i * 50 + 5 * i, 20, null);
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
    public void somethinHasChanged(Object arg0) {
        this.revalidate();
        this.repaint();
    }

    /**
     * Définit la visibilité des cartes.
     *
     * @param visibleCard Vrai si les cartes doivent être visibles, faux sinon.
     */
    public void setVisibleCard(boolean visibleCard) {
        this.visibleCard = visibleCard;
    }
}
