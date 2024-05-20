package view;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;

import model.Game;
import model.Token;
import model.observer.ModelListener;

/**
 * TokenView est un JPanel personnalisé qui affiche les jetons d'un type spécifique.
 */
public class TokenView extends JPanel implements ModelListener {
    
    /**
     * Les jetons du jeu.
     */
    private Token token;

    /**
     * La couleur des jetons affichés (bleu ou rouge).
     */
    private String color;

    /**
     * Constructeur pour créer une vue des jetons pour un type spécifique.
     *
     * @param game  Le modèle de jeu.
     * @param color La couleur des jetons ("blue" ou "red").
     */
    public TokenView(Game game, String color) {
        this.color = color;
        this.token = color.equals("blue") ? game.getBlueTokens() : game.getRedTokens();
        this.token.addModelListener(this);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawTokens(g);
    }

    /**
     * Dessine les jetons.
     *
     * @param g Le contexte graphique dans lequel dessiner.
     */
    private void drawTokens(Graphics g) {
        g.drawString(this.color.toUpperCase() + " TOKENS : " , 0, 53);
        g.setColor(color.equals("blue") ? Color.BLUE : Color.RED);
        int tokenCount = token.getTokenNumber();
        for (int i = 0; i < tokenCount; i++) {
            g.fillOval(100 + (i * 20), 40, 15, 15); 
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
