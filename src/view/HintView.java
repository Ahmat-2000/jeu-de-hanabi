package view;

import java.awt.Graphics;
import javax.swing.JPanel;

import model.Player;
import model.PlayerHint;
import model.observer.ModelListener;
import java.util.List;

/**
 * HintView est un JPanel personnalisé qui affiche les indices reçus par un joueur.
 */
public class HintView extends JPanel implements ModelListener {

    /**
     * Les indices reçus par les joueurs.
     */
    private PlayerHint hints;

    /**
     * Le joueur auquel les indices sont destinés.
     */
    private Player player;

    /**
     * Constructeur pour créer une vue des indices pour un joueur spécifique.
     *
     * @param p     Le joueur pour lequel les indices sont affichés.
     * @param hints Le modèle des indices des joueurs.
     */
    public HintView(Player p, PlayerHint hints) {
        this.hints = hints;
        this.player = p;
        this.hints.addModelListener(this);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.drawHints(g);
    }

    /**
     * Dessine les indices reçus par le joueur.
     *
     * @param g Le contexte graphique dans lequel dessiner.
     */
    private void drawHints(Graphics g) {
        int x = 10;
        int y = 40;
        int lineHeight = g.getFontMetrics().getHeight();
        List<String> receivedHints = hints.getHints(this.player);
        for (String hint : receivedHints) {
            g.drawString(hint, x, y);
            y += lineHeight;
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
