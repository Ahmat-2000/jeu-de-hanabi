package model;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Représente une carte avec une valeur et une couleur.
 */
public class Card {
    /**
     * La valeur minimale possible pour une carte.
     */
    private static final int lowestValue = 1;

    /**
     * La valeur maximale possible pour une carte.
     */
    private static final int highestValue = 5;

    /**
     * La valeur de la carte.
     */
    final int value;

    /**
     * La couleur de la carte.
     */
    private final CardColor color;

    /**
     * Indique si la carte est visible.
     */
    private boolean isvisible;

    /**
     * Indique si un indice de couleur a été reçu pour cette carte.
     */
    private boolean colorHintReceived;

    /**
     * Indique si un indice de valeur a été reçu pour cette carte.
     */
    private boolean valueHintReceived;

    /**
     * Les cartes possibles que cette carte pourrait être, selon les indices reçus.
     */
    private Set<Card> possibleCards; 


    /**
     * Construit une carte avec une valeur et une couleur.
     *
     * @param value La valeur de la carte.
     * @param color La couleur de la carte.
     */
    public Card(int value, CardColor color) {
        if (value < lowestValue || value > highestValue) {
            throw new IllegalArgumentException("La valeur de la carte doit être comprise entre [" + lowestValue + "," + highestValue + "]");
        }
        this.value = value;
        this.color = Objects.requireNonNull(color);
        this.colorHintReceived = false;
        this.valueHintReceived = false;
        this.possibleCards = new HashSet<>();
        this.isvisible = false;
    }

    /**
     * Récupère la couleur de la carte.
     *
     * @return La couleur de la carte.
     */
    public CardColor getColor() {
        return color;
    }

    /**
     * Récupère la valeur de la carte.
     *
     * @return La valeur de la carte.
     */
    public int getValue() {
        return value;
    }

    /**
     * Récupère la valeur maximale possible d'une carte.
     *
     * @return La valeur maximale possible.
     */
    public static int getHighestValue() {
        return highestValue;
    }

    /**
     * Récupère la valeur minimale possible d'une carte.
     *
     * @return La valeur minimale possible.
     */
    public static int getLowestValue() {
        return lowestValue;
    }

    @Override
    public String toString() {
        return "*" + value + color + "*";
    }

    /**
     * Récupère le nombre total de cartes d'une valeur et couleur spécifiques.
     *
     * @param value La valeur de la carte.
     * @param color La couleur de la carte.
     * @return Le nombre total de cartes.
     */
    public static int getTotalCount(int value, CardColor color) {
        switch (value) {
            case 1:
                return 3;
            case 2:
            case 3:
            case 4:
                return 2;
            case 5:
                return 1;
            default:
                return 0;
        }
    }

    @Override
    public int hashCode() {
        // Calcule le code de hachage en utilisant à la fois la couleur et la valeur de la carte.
        return Objects.hash(color, value);
    }

    @Override
    public boolean equals(Object obj) {
        // Vérifie si l'objet courant et l'objet passé en argument sont le même objet en mémoire.
        if (this == obj) {
            return true;
        }
        // Vérifie si l'objet passé en argument est une instance de la classe Card.
        if (!(obj instanceof Card)) {
            return false;
        }
        // Cast l'objet passé en argument en type Card pour pouvoir comparer ses champs.
        Card other = (Card) obj;
        // Retourne true si les deux objets ont la même couleur et la même valeur.
        return color.equals(other.color) && value == other.value;
    }

    /**
     * Détermine si un indice de couleur a été reçu.
     *
     * @return Vrai si un indice de couleur a été reçu, faux sinon.
     */
    public boolean hasColorHintReceived() {
        return colorHintReceived;
    }

    /**
     * Détermine si un indice de valeur a été reçu.
     *
     * @return Vrai si un indice de valeur a été reçu, faux sinon.
     */
    public boolean hasValueHintReceived() {
        return valueHintReceived;
    }

    /**
     * Définit si un indice de couleur a été reçu.
     *
     * @param received Vrai si un indice de couleur a été reçu, faux sinon.
     */
    public void setColorHintReceived(boolean received) {
        this.colorHintReceived = received;
    }

    /**
     * Définit si un indice de valeur a été reçu.
     *
     * @param received Vrai si un indice de valeur a été reçu, faux sinon.
     */
    public void setValueHintReceived(boolean received) {
        this.valueHintReceived = received;
    }

    /**
     * Récupère les cartes possibles.
     *
     * @return Les cartes possibles.
     */
    public Set<Card> getPossibleCards() {
        return possibleCards;
    }

    /**
     * Définit les cartes possibles.
     *
     * @param possibleCards Les cartes possibles.
     */
    public void setPossibleCards(Set<Card> possibleCards) {
        this.possibleCards = possibleCards;
    }

    /**
     * Détermine si la carte est partiellement connue.
     *
     * @return Vrai si la carte est partiellement connue, faux sinon.
     */
    public boolean isPartiallyKnown() {
        return colorHintReceived || valueHintReceived;
    }

    /**
     * Détermine si la carte est complètement connue.
     *
     * @return Vrai si la carte est complètement connue, faux sinon.
     */
    public boolean isFullyKnown() {
        return colorHintReceived && valueHintReceived;
    }

    /**
     * Récupère la couleur connue de la carte.
     *
     * @return La couleur connue de la carte, ou null si non connue.
     */
    public CardColor getKnownColor() {
        return colorHintReceived ? color : null;
    }

    /**
     * Récupère la valeur connue de la carte.
     *
     * @return La valeur connue de la carte, ou 0 si non connue.
     */
    public int getKnownValue() {
        return valueHintReceived ? value : 0;
    }

    /**
     * Récupère si la carte est visible.
     *
     * @return Vrai si la carte est visible, faux sinon.
     */
    public boolean isIsvisible() {
        return isvisible;
    }

    /**
     * Définit si la carte est visible.
     *
     * @param isvisible Vrai si la carte est visible, faux sinon.
     */
    public void setIsvisible(boolean isvisible) {
        this.isvisible = isvisible;
    }

}
