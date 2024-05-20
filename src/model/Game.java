package model;

import java.util.*;

/**
 * La classe Game représente une partie du jeu.
 * Elle gère le deck, les joueurs, les cartes jouées, les cartes défaussées, les indices et les jetons.
 */
public class Game {
    /**
     * Le paquet de cartes du jeu.
     */
    private final Deck deck;

    /**
     * Les cartes défaussées du jeu.
     */
    private DiscardedCards discard;

    /**
     * Le plateau de jeu.
     */
    private Bord bord;

    /**
     * Les jetons bleus utilisés pour donner des indices.
     */
    private final Token blueTokens;

    /**
     * Les jetons rouges utilisés pour indiquer les erreurs.
     */
    private final Token redTokens;

    /**
     * La liste des joueurs participant au jeu.
     */
    private final List<Player> players;

    /**
     * Les indices donnés aux joueurs.
     */
    private final PlayerHint hints;

    /**
     * Le nombre de joueurs participant au jeu.
     */
    private final int nbPlayers;

    /**
     * Le joueur actuellement en train de jouer.
     */
    private Player actualPlayer;

    /**
     * Les cartes qui ont été jouées sur le plateau.
     */
    private Set<Card> playedCards;

    /**
     * Les cartes qui ont été défaussées.
     */
    private Set<Card> discardedCards;

    /**
     * Les statistiques des cartes dans le jeu.
     */
    private Map<Card, Integer> cardStatistics;


    /**
     * Constructeur pour créer un jeu avec une liste de joueurs et un deck.
     *
     * @param players La liste des joueurs.
     * @param deck Le deck de cartes.
     */
    public Game(List<Player> players, Deck deck) {
        this.players = players;
        this.nbPlayers = players.size();
        this.deck = deck;
        this.actualPlayer = players.get(0);
        this.hints = new PlayerHint(this);
        this.discard = new DiscardedCards();
        this.bord = new Bord();
        this.blueTokens = new Token("blue", 8);
        this.redTokens = new Token("red", 3);
        this.discardedCards = new HashSet<>();
        this.playedCards = new HashSet<>();
        this.cardStatistics = new HashMap<>();
        initializeCardStatistics();
    }

    /**
     * Initialise les statistiques des cartes.
     */
    private void initializeCardStatistics() {
        for (CardColor color : CardColor.values()) {
            for (int value = Card.getLowestValue(); value <= Card.getHighestValue(); value++) {
                cardStatistics.put(new Card(value, color), Card.getTotalCount(value, color));
            }
        }
    }

    /**
     * Met à jour les statistiques des cartes lorsque une carte est jouée ou défaussée.
     *
     * @param card La carte à mettre à jour.
     */
    public void updateCardStatistics(Card card) {
        cardStatistics.computeIfPresent(card, (k, v) -> v - 1);
    }

    /**
     * Change le joueur actuel au joueur suivant.
     */
    public void changeActualPlayer() {
        int index = players.indexOf(actualPlayer);
        this.actualPlayer = players.get((index + 1) % nbPlayers);
    }

    /**
     * Ajoute un indice pour un joueur.
     *
     * @param player Le joueur recevant l'indice.
     * @param hint L'indice donné.
     */
    public void addHint(Player player, String hint) {
        this.hints.addHint(player, hint);
    }

    /**
     * Ajoute une carte au plateau.
     *
     * @param card La carte à ajouter.
     * @return Vrai si la carte a été ajoutée, faux sinon.
     */
    public boolean addTobord(Card card) {
        return bord.addToTheBord(card);
    }

    /**
     * Défausse une carte et pioche une nouvelle carte du deck.
     *
     * @param card La carte à défausser.
     */
    public void discardCard(Card card) {
        this.actualPlayer.discardCard(card);
        this.actualPlayer.pickCardInDeck(deck);
        this.discard.addCard(card);
        if (!this.blueTokens.isFullToken()) {
            this.blueTokens.addToken();
        }
    }

    /**
     * Défausse une carte par son index et pioche une nouvelle carte du deck.
     *
     * @param index L'index de la carte à défausser.
     */
    public void discardCard(int index) {
        this.discardCard(this.actualPlayer.getCard(index));
    }

    /**
     * Joue une carte et pioche une nouvelle carte du deck.
     *
     * @param card La carte à jouer.
     */
    public void playCard(Card card) {
        this.actualPlayer.discardCard(card);
        this.actualPlayer.pickCardInDeck(deck);
        if (!addTobord(card)) {
            this.discard.addCard(card);
            this.redTokens.removeToken();
        } else {
            this.playedCards.add(card);
        }
    }

    /**
     * Joue une carte par son index et pioche une nouvelle carte du deck.
     *
     * @param index L'index de la carte à jouer.
     */
    public void playCard(int index) {
        this.playCard(this.actualPlayer.getCard(index));
    }

    /**
     * Vérifie si le jeu est terminé.
     *
     * @return Vrai si le jeu est terminé, faux sinon.
     */
    public boolean isGameOver() {
        if (this.redTokens.noTokenAvailable()) {
            return true;
        }
        if (bord.isCompleteFireworks()) {
            return true;
        }
        return deck.isEmpty();
    }

    /**
     * Vérifie si le jeu est gagné.
     *
     * @return Vrai si le jeu est gagné, faux sinon.
     */
    public boolean isGameWon() {
        return bord.isCompleteFireworks();
    }

    /**
     * Vérifie si des jetons bleus sont disponibles.
     *
     * @return Vrai si des jetons bleus sont disponibles, faux sinon.
     */
    public boolean haveBlueTokenAvailable() {
        return !this.blueTokens.noTokenAvailable();
    }

    /**
     * Utilise un jeton bleu.
     */
    public void useBlueToken() {
        if (this.haveBlueTokenAvailable()) {
            this.blueTokens.removeToken();
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("---------------------------------------------------------- Next round :--------------------------------------------------------------------- \n");
        sb.append("Deck contains " + deck.getSize() + " cards\n");
        sb.append("bord : " + bord.getFireworks().toString() + "\n");
        sb.append("Discard : " + discard.getcardBag().toString() + "\n");
        sb.append("Blue Tokens  : " + blueTokens.toString() + "\n");
        sb.append("Red Tokens  : " + redTokens.toString() + "\n");
        sb.append("List cards of other players : \n");
        for (Player player : players) {
            if (!player.equals(actualPlayer)) {
                sb.append(player.getName() + " : " + player.getHand().toString() + "\n");
            }
        }
        sb.append("List of hints by player : \n");
        for (Player player : hints.getHints().keySet()) {
            sb.append(player.getName() + " : " + hints.getHints().get(player).toString() + "\n");
        }

        sb.append("Actual player : " + actualPlayer.getName() + " has " + actualPlayer.getHand().getSize() + " cards in hand\n");
        sb.append("Nb players : " + nbPlayers + "\n");
        return sb.toString();
    }

    /**
     * Vérifie si une carte est jouable.
     *
     * @param c La carte à vérifier.
     * @return Vrai si la carte est jouable, faux sinon.
     */
    public boolean isPlayable(Card c) {
        int index = c.getColor().ordinal();
        // Vérifie si la pile est vide et si la carte a une valeur de 1.
        if (bord.getFireworks().get(index).isEmpty() && c.getValue() == 1) {
            return true;
        } else if (!bord.getFireworks().get(index).isEmpty() &&
                c.getValue() == bord.getFireworks().get(index).get(bord.getFireworks().get(index).size() - 1).getValue() + 1) {
            // Vérifie si la valeur de la carte est exactement de 1 plus grande que la dernière carte de la pile.

            return true;
        }
        return false;
    }

    /**
     * Obtient l'ensemble des cartes connues (jouées ou défaussées).
     *
     * @return L'ensemble des cartes connues.
     */
    public Set<Card> getKnownCards() {
        Set<Card> knownCards = new HashSet<>();
        knownCards.addAll(this.playedCards);
        knownCards.addAll(this.discardedCards);
        return knownCards;
    }

    /**
     * Obtient l'ensemble des cartes jouées.
     *
     * @return L'ensemble des cartes jouées.
     */
    public Set<Card> getPlayedCards() {
        return this.playedCards;
    }

    /**
     * Obtient l'ensemble des cartes défaussées.
     *
     * @return L'ensemble des cartes défaussées.
     */
    public Set<Card> getDiscardedCards() {
        return this.discardedCards;
    }

    /**
     * Obtient le nombre de cartes restantes d'un type spécifique.
     *
     * @param card La carte à vérifier.
     * @return Le nombre de cartes restantes.
     */
    public int getCardCount(Card card) {
        return cardStatistics.getOrDefault(card, 0);
    }

    /**
     * Obtient le deck de cartes.
     *
     * @return Le deck de cartes.
     */
    public Deck getDeck() {
        return deck;
    }

    /**
     * Obtient la liste des joueurs.
     *
     * @return La liste des joueurs.
     */
    public List<Player> getPlayers() {
        return players;
    }

    /**
     * Obtient les indices donnés par les joueurs.
     *
     * @return Une map des indices par joueur.
     */
    public Map<Player, List<String>> getHints() {
        return hints.getHints();
    }

    /**
     * Obtient les indices donnés aux joueurs.
     *
     * @return Les indices donnés aux joueurs.
     */
    public PlayerHint getPlayerHint() {
        return hints;
    }

    /**
     * Obtient le nombre de joueurs.
     *
     * @return Le nombre de joueurs.
     */
    public int getNbPlayers() {
        return nbPlayers;
    }

    /**
     * Obtient le joueur actuel.
     *
     * @return Le joueur actuel.
     */
    public Player getActualPlayer() {
        return actualPlayer;
    }

    /**
     * Obtient les cartes défaussées.
     *
     * @return Les cartes défaussées.
     */
    public DiscardedCards getDiscard() {
        return discard;
    }

    /**
     * Obtient le plateau de jeu.
     *
     * @return Le plateau de jeu.
     */
    public Bord getbord() {
        return bord;
    }

    /**
     * Obtient les jetons bleus.
     *
     * @return Les jetons bleus.
     */
    public Token getBlueTokens() {
        return blueTokens;
    }

    /**
     * Obtient les jetons rouges.
     *
     * @return Les jetons rouges.
     */
    public Token getRedTokens() {
        return redTokens;
    }

    /**
     * Obtient le score actuel du jeu.
     *
     * @return Le score actuel.
     */
    public int getScore() {
        return bord.countScore();
    }

    /**
     * Obtient un feedback basé sur le score actuel.
     *
     * @return Le feedback basé sur le score.
     */
    public String getScoreFeedback() {
        int score = this.getScore();

        if (score >= 25) {
            return "Parfait ! Un score parfait, bien joué !";
        } else if (score >= 20) {
            return "Impressionnant ! Vous avez excellé dans ce jeu.";
        } else if (score >= 15) {
            return "Bien fait ! C'est un bon résultat.";
        } else if (score >= 10) {
            return "Pas mal, mais vous pouvez faire mieux.";
        } else {
            return "Très très bas";
        }
    }
}
