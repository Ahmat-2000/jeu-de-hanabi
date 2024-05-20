package model;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;

/**
 * La classe Player représente un joueur dans le jeu.
 * Elle contient des informations sur le nom du joueur, sa main, et les joueurs associés.
 */
public class Player {
    /**
     * Le nom du joueur.
     */
    private final String name;

    /**
     * La main du joueur.
     */
    private final Hand hand;

    /**
     * Le nombre minimum de joueurs.
     */
    private static final int minPlayer = 2;

    /**
     * Le nombre maximum de joueurs.
     */
    private static final int maxPlayer = 5;

    /**
     * L'ensemble des joueurs.
     */
    private final Set<Player> players = new HashSet<>();

    /**
     * L'ensemble des indices reçus par le joueur.
     */
    private Set<String> receivedHints = new HashSet<>();


    /**
     * Constructeur pour créer un joueur avec un nom et une main.
     *
     * @param name Le nom du joueur.
     * @param hand La main du joueur.
     */
    public Player(String name, Hand hand) {
        this.name = Objects.requireNonNull(name);
        this.hand = hand;
    }

    /**
     * Constructeur par défaut pour créer un joueur sans nom et sans main.
     */
    public Player() {
        this.name = "";
        this.hand = null;
    }

    /**
     * Pioche une carte dans le deck et l'ajoute à la main du joueur.
     *
     * @param deck Le deck de cartes.
     */
    public void pickCardInDeck(Deck deck) {
        hand.pickCardInDeck(deck);
    }

    /**
     * Défausse une carte de la main du joueur.
     *
     * @param card La carte à défausser.
     */
    public void discardCard(Card card) {
        hand.discardCard(card);
    }

    /**
     * Obtient une carte à un index spécifique dans la main du joueur.
     *
     * @param index L'index de la carte.
     * @return La carte à l'index spécifié.
     */
    public Card getCard(int index) {
        return hand.getCard(index);
    }

    /**
     * Obtient la taille de la main du joueur.
     *
     * @return La taille de la main du joueur.
     */
    public int getHandSize() {
        return hand.getSize();
    }

    /**
     * Obtient le nom du joueur.
     *
     * @return Le nom du joueur.
     */
    public String getName() {
        return name;
    }

    /**
     * Obtient la main du joueur.
     *
     * @return La main du joueur.
     */
    public Hand getHand() {
        return hand;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Player)) {
            return false;
        }
        Player other = (Player) obj;
        return Objects.equals(name, other.name);
    }

    /**
     * Ajoute un nouveau joueur à l'ensemble des joueurs s'il n'existe pas déjà.
     *
     * @param newPlayer Le nouveau joueur à ajouter.
     * @return Vrai si le joueur a été ajouté, faux s'il existait déjà.
     */
    public boolean add(Player newPlayer) {
        if (!players.contains(newPlayer)) {
            players.add(newPlayer);
            return true;
        }
        return false;
    }

    /**
     * Obtient un itérateur sur l'ensemble des joueurs.
     *
     * @return Un itérateur sur l'ensemble des joueurs.
     */
    public Iterator<Player> getIterator() {
        return players.iterator();
    }

    /**
     * Vérifie si le nom spécifié n'est pas celui d'un joueur existant.
     *
     * @param name Le nom à vérifier.
     * @return Vrai si le nom n'est pas celui d'un joueur existant, faux sinon.
     */
    public boolean isNewPlayer(String name) {
        for (Player player : players) {
            if (player.getName().equals(name)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Obtient un joueur par son nom, différent du joueur actuel.
     *
     * @param name Le nom du joueur à obtenir.
     * @param actualPlayer Le joueur actuel.
     * @return Le joueur trouvé.
     * @throws IllegalStateException Si le joueur n'existe pas.
     */
    public Player getPlayer(String name, Player actualPlayer) {
        for (Player player : players) {
            if (player.getName().equals(name) && !actualPlayer.getName().equals(name)) {
                return player;
            }
        }
        throw new IllegalStateException("ce joueur n'existe pas");
    }

    /**
     * Obtient le nombre minimum de joueurs.
     *
     * @return Le nombre minimum de joueurs.
     */
    public static int getMinPlayers() {
        return minPlayer;
    }

    /**
     * Obtient le nombre maximum de joueurs.
     *
     * @return Le nombre maximum de joueurs.
     */
    public static int getMaxPlayers() {
        return maxPlayer;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        for (Player player : players) {
            str.append(player).append("\n");
        }
        return str.length() > 0 ? str.substring(0, str.length() - 1) : "";
    }

    /**
     * Ajoute un indice reçu à l'ensemble des indices reçus par le joueur.
     *
     * @param hint L'indice reçu.
     */
    public void addReceivedHint(String hint) {
        receivedHints.add(hint);
    }

    /**
     * Vérifie si un indice a déjà été reçu par le joueur.
     *
     * @param hint L'indice à vérifier.
     * @return Vrai si l'indice a déjà été reçu, faux sinon.
     */
    public boolean hasReceivedHint(String hint) {
        return receivedHints.contains(hint);
    }
}
