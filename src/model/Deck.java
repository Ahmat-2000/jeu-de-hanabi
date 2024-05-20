package model;

import java.util.Collections;
import java.util.Stack;

import model.observer.AbstractListenableModel;

/**
 * La classe Deck représente un paquet de cartes dans le jeu.
 * Elle hérite de AbstractListenableModel pour notifier les observateurs des changements.
 */
public class Deck extends AbstractListenableModel {

    /**
     * Pile des cartes
     */
    private final Stack<Card> deck = new Stack<>();

    /**
     * Constructeur pour créer un deck et l'initialiser.
     */
    public Deck() {
        super();
        initializeDeck();
    }

    /**
     * Initialise le deck avec les cartes appropriées et les mélange.
     */
    private void initializeDeck() {
        for (CardColor color : CardColor.values()) {
            for (int i = 0; i < 10; i++) {
                if (i < 3) {
                    deck.push(new Card(1, color));
                } else if (i < 5) {
                    deck.push(new Card(2, color));
                } else if (i < 7) {
                    deck.push(new Card(3, color));
                } else if (i < 9) {
                    deck.push(new Card(4, color));
                } else {
                    deck.push(new Card(5, color));
                }
            }
        }
        Collections.shuffle(deck);
        super.fireChange();
    }

    /**
     * Vérifie si le deck est vide.
     *
     * @return Vrai si le deck est vide, faux sinon.
     */
    public boolean isEmpty() {
        return deck.isEmpty();
    }

    /**
     * Obtient la taille actuelle du deck.
     *
     * @return La taille du deck.
     */
    public int getSize() {
        return deck.size();
    }

    /**
     * Pioche une carte du deck.
     *
     * @return La carte piochée.
     */
    public Card pickCard() {
        super.fireChange();
        return deck.pop();
    }

    /**
     * Obtient le nombre total d'occurrences d'une carte spécifique dans le deck.
     *
     * @param card La carte à vérifier.
     * @return Le nombre total d'occurrences de la carte.
     */
    public long getTotalCount(Card card) {
        long count = 0;
        for (Card c : deck) {
            if (c.equals(card)) {
                count++;
            }
        }
        return count;
    }

    /**
     * Obtient le deck de cartes.
     *
     * @return Le deck de cartes.
     */
    public Stack<Card> getDeck() {
        return deck;
    }
}
