package model;

import java.util.ArrayList;
import model.observer.AbstractListenableModel;

/**
 * Représente la main d'un joueur dans le jeu.
 */
public class Hand extends AbstractListenableModel {
    private ArrayList<Card> hand = new ArrayList<Card>();

    /**
     * Construit une main avec un certain nombre de cartes du paquet.
     *
     * @param deck Le paquet de cartes.
     * @param numberCards Le nombre de cartes à piocher.
     */
    public Hand(Deck deck, int numberCards) {
        super();
        for (int i = 0; i < numberCards; i++) {
            pickCardInDeck(deck);
        }
    }

    /**
     * Définit la main du joueur.
     *
     * @param hand La nouvelle main.
     */
    public void setHand(ArrayList<Card> hand) {
        this.hand = hand;
    }

    /**
     * Ajoute une carte à la main en la piochant dans le paquet.
     *
     * @param deck Le paquet de cartes.
     */
    public void pickCardInDeck(Deck deck) {
        hand.add(deck.pickCard());
        super.fireChange();
    }

    /**
     * Obtient la taille de la main.
     *
     * @return La taille de la main.
     */
    public int getSize() {
        return hand.size();
    }

    /**
     * Retire une carte de la main.
     *
     * @param card La carte à retirer.
     */
    public void discardCard(Card card) {
        hand.remove(card);
        super.fireChange();
    }

    /**
     * Vérifie si la main contient une carte spécifique.
     *
     * @param card La carte à vérifier.
     * @return Vrai si la carte est présente dans la main, faux sinon.
     */
    public boolean handContains(Card card) {
        return hand.contains(card);
    }

    /**
     * Obtient une carte par son index dans la main.
     *
     * @param index L'index de la carte.
     * @return La carte à l'index spécifié.
     */
    public Card getCard(int index) {
        return hand.get(index);
    }

    /**
     * Récupère les cartes dans la main.
     *
     * @return La liste des cartes dans la main.
     */
    public ArrayList<Card> getCards() {
        return hand;
    }

    /**
     * Obtient une chaîne représentant les positions des cartes ayant une couleur spécifique.
     *
     * @param color La couleur des cartes.
     * @return Une chaîne représentant les positions des cartes.
     */
    public String cardsPositionsByColor(CardColor color) {
        ArrayList<Integer> list = new ArrayList<Integer>();
        for (int i = 0; i < hand.size(); i++) {
            if (hand.get(i).getColor().equals(color)) {
                list.add(i + 1);
            }
        }
        return list.toString();
    }

    /**
     * Obtient une chaîne représentant les positions des cartes ayant une valeur spécifique.
     *
     * @param value La valeur des cartes.
     * @return Une chaîne représentant les positions des cartes.
     */
    public String cardsPositionsByValue(int value) {
        ArrayList<Integer> list = new ArrayList<Integer>();
        for (int i = 0; i < hand.size(); i++) {
            if (hand.get(i).getValue() == value) {
                list.add(i + 1);
            }
        }
        return list.toString();
    }

    @Override
    public String toString() {
        return hand.toString();
    }
}
