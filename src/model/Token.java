package model;

import model.observer.AbstractListenableModel;

/**
 * La classe Token représente un jeton dans le jeu.
 */
public class Token extends AbstractListenableModel {
    /** 
     * Nombre maximal de Jetons 
    */
    protected int maxTokenValue;
    /** 
     * Nombre courant de Jetons
     */
    private int tokenNumber;
    @SuppressWarnings("unused")
    /** La couleur du Jeton */
    private String color;

    /**
     * Constructeur pour créer un jeton avec une couleur et une valeur maximale.
     *
     * @param color La couleur du jeton.
     * @param maxTokenValue La valeur maximale du jeton.
     */
    public Token(String color, int maxTokenValue) {
        super();
        this.tokenNumber = maxTokenValue;
        this.maxTokenValue = maxTokenValue;
        this.color = color;
    }

    /**
     * Obtient le nombre actuel de jetons.
     *
     * @return Le nombre de jetons.
     */
    public int getTokenNumber() {
        return tokenNumber;
    }

    /**
     * Ajoute un jeton.
     */
    public void addToken() {
        this.tokenNumber++;
        super.fireChange();
    }

    /**
     * Retire un jeton.
     */
    public void removeToken() {
        if (!noTokenAvailable()) {
            this.tokenNumber--;
            super.fireChange();
        }
    }

    /**
     * Vérifie si aucun jeton n'est disponible.
     *
     * @return Vrai si aucun jeton n'est disponible, faux sinon.
     */
    public boolean noTokenAvailable() {
        return this.tokenNumber == 0;
    }

    /**
     * Vérifie si le nombre de jetons est complet.
     *
     * @return Vrai si le nombre de jetons est complet, faux sinon.
     */
    public boolean isFullToken() {
        return tokenNumber == maxTokenValue;
    }

    @Override
    public String toString() {
        return "" + this.getTokenNumber();
    }

    /**
     * Définit le nombre de jetons.
     *
     * @param tokenNumber Le nouveau nombre de jetons.
     */
    public void setTokenNumber(int tokenNumber) {
        this.tokenNumber = tokenNumber;
    }
}
