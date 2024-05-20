package model;

import java.util.ArrayDeque;
import java.util.Deque;

import model.observer.AbstractListenableModel;

/**
 * La classe DiscardedCards représente les cartes défaussées dans le jeu.
 */
public class DiscardedCards extends AbstractListenableModel {

    /**
     * Liste de cartes défaussées
     */
    private Deque<Card> cardBag;

    /**
     * Constructeur pour créer un sac de cartes défaussées.
     */
    public DiscardedCards() {
        super();
        this.cardBag = new ArrayDeque<>();
    }

    /**
     * Ajoute une carte au sac de cartes défaussées.
     *
     * @param card La carte à ajouter.
     */
    public void addCard(Card card) {
        this.cardBag.add(card);
        super.fireChange();
    }

    /**
     * Nettoie le sac de cartes défaussées.
     */
    public void clean() {
        this.cardBag.clear();
        super.fireChange();
    }

    /**
     * Obtient le sac de cartes défaussées.
     *
     * @return Le sac de cartes défaussées.
     */
    public Deque<Card> getcardBag() {
        return this.cardBag;
    }

    @Override
    public String toString() {
        return "PlayedCards : " + this.cardBag;
    }
}
