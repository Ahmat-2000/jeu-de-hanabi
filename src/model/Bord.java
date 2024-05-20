package model;

import java.util.ArrayList;
import java.util.List;
import model.observer.AbstractListenableModel;

/**
 * Cette classe représente un feu d'artifice pour le jeu Hanabi.
 * Elle contient une liste de listes de cartes, une pour chaque couleur.
 */
public class Bord extends AbstractListenableModel {

    /** Taille d'une pile du feu d'artifice */
    private int stackSize;
    /** La liste des piles du feu d'artifice */
    private List<List<Card>> fireworks;

    /**
     * Constructeur pour créer un Bord.
     */
    public Bord() {
        super();
        this.stackSize = 5;
        this.fireworks = new ArrayList<>();
        this.prepareBord();
    }

    /**
     * Prépare les piles de bord pour un nouveau jeu.
     */
    @SuppressWarnings("unused")
    private void prepareBord() {
        for (CardColor color : CardColor.values()) {
            this.fireworks.add(new ArrayList<>());
        }
    }

    /**
     * Nettoie les piles de bord pour un nouveau jeu.
     */
    public void cleanBord() {
        for (List<Card> stack : this.fireworks) {
            stack.clear();
        }
        super.fireChange();
    }

    /**
     * Vérifie si une carte peut être ajoutée au bord.
     *
     * @param card La carte à ajouter.
     * @return Vrai si la carte peut être ajoutée, faux sinon.
     */
    public boolean canAddCard(Card card) {
        List<Card> stack = this.fireworks.get(card.getColor().ordinal());
        if (stack.size() == this.stackSize) {
            return false;
        }
        if (!stack.isEmpty() && card.getValue() != (stack.get(stack.size() - 1).getValue() + 1)) {
            return false;
        }
        if (stack.isEmpty() && card.getValue() != 1) {
            return false;
        }
        return true;
    }

    /**
     * Ajoute une nouvelle carte au bord.
     *
     * @param card La carte à ajouter.
     * @return Vrai si la carte a été ajoutée, faux sinon.
     */
    public boolean addToTheBord(Card card) {
        if (this.canAddCard(card)) {
            this.fireworks.get(card.getColor().ordinal()).add(card);
            super.fireChange();
            return true;
        }
        return false;
    }

    /**
     * Vérifie si les feux d'artifice sont complets.
     *
     * @return Vrai si les feux d'artifice sont complets, faux sinon.
     */
    public boolean isCompleteFireworks() {
        for (List<Card> stack : this.fireworks) {
            if (stack.size() != stackSize) {
                return false;
            }
        }
        return true;
    }

    /**
     * Compte le score actuel des feux d'artifice.
     *
     * @return Le score actuel.
     */
    public int countScore() {
        int score = 0;
        for (List<Card> stack : this.fireworks) {
            score += stack.size();
        }
        return score;
    }

    @Override
    public String toString() {
        StringBuilder tmp = new StringBuilder();
        for (List<Card> stack : this.fireworks) {
            tmp.append(stack.toString()).append("\n");
        }
        return tmp.toString();
    }

    /**
     * Obtient les feux d'artifice.
     *
     * @return La liste des feux d'artifice.
     */
    public List<List<Card>> getFireworks() {
        return fireworks;
    }
}
