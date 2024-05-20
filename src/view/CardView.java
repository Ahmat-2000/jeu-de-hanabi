package view;

import javax.swing.ImageIcon;

import model.Card;

import java.awt.*;

/**
 * CardView est une classe qui représente graphiquement une carte.
 */
public class CardView {
    /**
     * La carte à afficher.
     */
    private Card card;

    /**
     * Le chemin de l'image de la carte.
     */
    private String pathname;

    /**
     * L'image de la carte.
     */
    private Image cardImage;

    /**
     * Constructeur pour créer une vue de carte avec la carte spécifiée.
     *
     * @param card La carte à afficher.
     */
    public CardView(Card card) {
        this.card = card;
        this.buildImage();
    }

    /**
     * Construit l'image de la carte en fonction de sa visibilité et de ses attributs.
     */
    private void buildImage() {
        this.pathname = "/content/images/back.png";

        if (this.card.isIsvisible()) {
            this.pathname = "/content/images/" + this.card.getColor() + "-" +this.card.getValue() + ".png";
        }
        try {
            ImageIcon Icon = new ImageIcon(getClass().getResource(this.pathname));
            Image img = Icon.getImage();  
            this.cardImage = img.getScaledInstance(50, 50, Image.SCALE_SMOOTH);  
            this.cardImage = new ImageIcon(this.cardImage).getImage();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Obtient l'image de la carte.
     *
     * @return L'image de la carte.
     */
    public Image getCardImage() {
        return this.cardImage;
    }
}
