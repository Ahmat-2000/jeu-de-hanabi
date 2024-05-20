package view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.util.List;

import javax.swing.JPanel;

import model.Bord;
import model.Card;
import model.CardColor;
import model.Game;
import model.observer.ModelListener;

/**
 * BordView est un JPanel personnalisé qui affiche les feux d'artifice sur le plateau de jeu.
 */
public class BordView extends JPanel implements ModelListener {
    
    /**
     * Le bord de jeu représentant les feux d'artifice.
     */
    private Bord bord;

    /**
     * Constructeur pour créer un BordView avec le modèle de jeu spécifié.
     *
     * @param game Le modèle de jeu.
     */
    public BordView(Game game) {
        this.bord = game.getbord();
        this.bord.addModelListener(this);
        setPreferredSize(new Dimension(300, 80));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int c = 20;
        c = this.getWidth() / 2 - 150;
        int i = 0;
        for (List<Card> entry : this.bord.getFireworks()) {
            if (entry.size() == 0) {
                g.drawImage(new CardView(new Card(1, CardColor.WHITE)).getCardImage(), c + i * 50 + 5 * i, 20, null);
            } else {
                Card card = entry.get(entry.size() - 1);
                card.setIsvisible(true);
                g.drawImage(new CardView(card).getCardImage(), c + i * 50 + 5 * i, 20, null);
            }
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
}
