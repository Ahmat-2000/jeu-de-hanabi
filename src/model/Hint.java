package model;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * La classe Hint représente les indices donnés aux joueurs dans le jeu.
 */
public class Hint {

    /**
     * Génère un indice basé sur la couleur des cartes dans la main d'un joueur.
     *
     * @param color La couleur des cartes.
     * @param hand La main du joueur.
     * @return Un indice indiquant les positions des cartes de la couleur spécifiée.
     */
    public static String colorHint(CardColor color, Hand hand) {
        String hint = "Tu as un ou des cartes de couleur " + color.toString() + " en position : ";
        for (int i = 0; i < hand.getSize(); i++) {
            if (hand.getCard(i).getColor().equals(color)) {
                hint += (i + 1) + " ";
            }
        }
        return hint;
    }

    /**
     * Génère un indice basé sur la valeur des cartes dans la main d'un joueur.
     *
     * @param value La valeur des cartes.
     * @param hand La main du joueur.
     * @return Un indice indiquant les positions des cartes de la valeur spécifiée.
     */
    public static String valueHint(int value, Hand hand) {
        String hint = "Tu as une ou des cartes de valeur " + value + " en position : ";
        for (int i = 0; i < hand.getSize(); i++) {
            if (hand.getCard(i).getValue() == value) {
                hint += (i + 1) + " ";
            }
        }
        return hint;
    }

    /**
     * Génère un indice basé sur la valeur des cartes dans la main d'un joueur en tenant compte du jeu actuel.
     *
     * @param value La valeur des cartes.
     * @param hand La main du joueur.
     * @param game L'état actuel du jeu.
     * @return Un indice indiquant les positions des cartes de la valeur spécifiée, avec les cartes jouables en priorité.
     */
    public static String valueHint(int value, Hand hand, Game game) {
        String hint = "Tu as une ou des cartes de valeur " + value + " en position : ";
        Map<Card, Integer> cardsToPlay = new HashMap<>();
        for (int i = 0; i < hand.getSize(); i++) {
            if (hand.getCard(i).getValue() == value) {
                cardsToPlay.put(hand.getCard(i), i);
            }
        }

        LinkedList<Integer> playableCards = new LinkedList<>();
        for (Card card : cardsToPlay.keySet()) {
            if (game.isPlayable(card)) {
                playableCards.addFirst(cardsToPlay.get(card));
            } else {
                playableCards.addLast(cardsToPlay.get(card));
            }
        }

        for (int i : playableCards) {
            hint += (i + 1) + " ";
        }

        return hint;
    }
}
