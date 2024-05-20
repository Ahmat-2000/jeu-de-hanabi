package view;

import model.Player;

import java.util.Map;

/**
 * Représente la vue pour une partie de Hanabi.
 */
public interface GameView {

    /**
     * Affiche l'état actuel du jeu.
     */
    void showGame();

    /**
     * Interroge un utilisateur sur la prochaine action à effectuer.
     *
     * @param player Le joueur actuel.
     * @return Un entier représentant le type d'action choisi (1 pour jouer une carte, 2 pour défausser une carte, 3 pour donner un indice).
     */
    int askPlayType(Player player);

    /**
     * Interroge un utilisateur sur la carte à jouer.
     *
     * @param player Le joueur actuel.
     * @return L'index de la carte choisie.
     */
    int askCard(Player player);

    /**
     * Interroge l'utilisateur sur le nombre de joueurs et les types de joueurs (humains et IA).
     *
     * @return Un tableau contenant le nombre total de joueurs, le nombre de joueurs IA standard,
     *         le nombre de AIMalinPlayers et le nombre de AIAdvancedPlayers.
     */
    int[] askPlayersSetup();

    /**
     * Interroge l'utilisateur sur le joueur à qui donner un indice.
     *
     * @param player Le joueur actuel.
     * @return L'index du joueur choisi.
     */
    int askPlayer(Player player);

    /**
     * Interroge l'utilisateur sur l'indice à donner et à quel joueur.
     *
     * @param player Le joueur actuel.
     * @return Une map contenant le joueur cible et l'indice donné.
     */
    Map<Player, String> askHint(Player player);
}
