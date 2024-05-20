package model.strategies;

import model.*;

/**
 * Interface IA représentant les actions possibles pour un joueur IA dans le jeu.
 */
public interface IA {
    
    /**
     * Choisit l'action à effectuer par l'IA pendant son tour.
     *
     * @param game L'état actuel du jeu.
     */
    void chooseAction(Game game); 

    /**
     * Joue une carte de la main du joueur.
     *
     * @param game L'état actuel du jeu.
     */
    void playCard(Game game); 

    /**
     * Défausse une carte de la main du joueur.
     *
     * @param game L'état actuel du jeu.
     */
    void discardCard(Game game); 

    /**
     * Donne un indice à un autre joueur.
     *
     * @param game L'état actuel du jeu.
     */
    void giveHint(Game game); 

    /**
     * Détermine si l'IA devrait jouer une carte.
     *
     * @param game L'état actuel du jeu.
     * @return Vrai si une carte doit être jouée, faux sinon.
     */
    boolean shouldPlayCard(Game game); 

    /**
     * Détermine si l'IA devrait donner un indice.
     *
     * @param game L'état actuel du jeu.
     * @return Vrai si un indice doit être donné, faux sinon.
     */
    boolean shouldGiveHint(Game game); 

    /**
     * Choisit une carte à jouer.
     *
     * @param game L'état actuel du jeu.
     * @return La carte choisie à jouer.
     */
    Card chooseCardToPlay(Game game); 

    /**
     * Choisit une carte à défausser.
     *
     * @param game L'état actuel du jeu.
     * @return La carte choisie à défausser.
     */
    Card chooseCardToDiscard(Game game); 

    /**
     * Choisit un joueur à qui donner un indice.
     *
     * @param game L'état actuel du jeu.
     * @return Le joueur choisi pour recevoir un indice.
     */
    Player choosePlayerToGiveHint(Game game); 

    /**
     * Choisit un indice à donner.
     *
     * @param game L'état actuel du jeu.
     * @return L'indice choisi.
     */
    String chooseHint(Game game); 
}
