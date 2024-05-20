package model.strategies;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import model.*;

/**
 * AIMalinPlayer représente un joueur IA avec des capacités de jeu optimisées
 * pour analyser les indices et prendre des décisions stratégiques.
 */
public class AIMalinPlayer extends AIPlayer {
    /**
     * Une liste chaînée pour stocker les positions des cartes à jouer.
     */
    private LinkedList<Integer> cardsToPlay;

    /**
     * Un ensemble pour stocker les positions des cartes à défausser.
     */
    private Set<Integer> cardsToDiscard;

    /**
     * Une map pour stocker les ensembles de positions de cartes à jouer, organisées par couleur.
     */
    protected Map<CardColor, Set<Integer>> cardsToPlayByColor;

    /**
     * Une map pour stocker les ensembles de positions de cartes à jouer, organisées par valeur.
     */
    protected Map<Integer, Set<Integer>> cardsToPlayByValue;

    /**
     * Une map pour stocker les cartes connues et leurs positions.
     */
    public Map<Card, Integer> knownCards;

    /**
     * Constructeur pour AIMalinPlayer.
     *
     * @param name Nom du joueur.
     * @param hand Main du joueur.
     */
    public AIMalinPlayer(String name, Hand hand) {
        super(name, hand);

        this.cardsToPlay = new LinkedList<>();
        this.cardsToDiscard = new HashSet<>();
        this.cardsToPlayByColor = new HashMap<>();
        this.cardsToPlayByValue = new HashMap<>();
        this.knownCards = new HashMap<>();

        for (int i = 0; i < 5; i++) {
            cardsToPlayByValue.put(i + 1, new HashSet<>());
        }

        for (CardColor color : CardColor.values()) {
            cardsToPlayByColor.put(color, new HashSet<>());
        }
    }

    /**
     * Choisit l'action à effectuer par l'IA pendant son tour.
     *
     * @param game L'état actuel du jeu.
     */
    @Override
    public void chooseAction(Game game) {
        // Analyse les indices reçus et les cartes jouables pour choisir la meilleure action
        System.out.println("IA " + getName() + " analyse les indices reçus");
        analyseHints(game);
        if (shouldPlayCard(game)) {
            System.out.println("IA " + getName() + " joue une carte");
            playCard(game);
        } else if (shouldDiscardCard(game)){
            System.out.println("IA " + getName() + " défausse une carte");
            discardCard(game);
        } else {
            System.out.println("IA " + getName() + " donne un indice");
            giveHint(game);
        }
    }

    /**
     * Analyse les indices reçus pour déterminer les cartes à jouer ou à défausser.
     *
     * @param game L'état actuel du jeu.
     */
    protected void analyseHints(Game game) {
        List<String> hints = game.getHints().getOrDefault(this, List.of());
        // On commence les indices par les plus récents
        for (String hint : hints) {
            System.out.println("IA " + getName() + " analyse l'indice " + hint);
            // Vérifie si l'indice est une couleur
            if (hint.contains("couleur")) {
                // Récupère la couleur entre le mot "couleur " et le mot " en"
                CardColor color = CardColor.valueOf(hint.split("couleur ")[1].split(" en")[0]);
                // Récupère les positions des cartes de cette couleur après le ": "
                String[] positions = hint.split(": ")[1].split(" ");
                Set<Integer> cards = new HashSet<>();
                for (String position : positions) {
                    cards.add(Integer.parseInt(position) - 1);
                }
                this.cardsToPlayByColor.put(color, cards);
            } else {
                // Récupère la valeur entre le mot "valeur " et le mot " en"
                int value = Integer.parseInt(hint.split("valeur ")[1].split(" en")[0]);
                // Récupère les positions des cartes de cette valeur après le ": "
                String[] positions = hint.split(": ")[1].split(" ");
                List<Integer> cards = new ArrayList<>();
                for (String position : positions) {
                    cards.add(Integer.parseInt(position) - 1);
                }
                // Si la valeur est 5, on ne peut pas la défausser
                if (value == 5) {
                    cardsToDiscard.removeAll(cards);
                } else {
                    // On vérifie si on peut jouer une carte de cette valeur sur le plateau
                    boolean mayBePlayed = false;
                    for (int i : cards) {
                        if ((!mayBePlayed || value == 1) && game.isPlayable(getCard(i))) {
                            cardsToPlay.add(i);

                            cardsToDiscard.remove(i);
                            mayBePlayed = true;
                        } else {
                            cardsToDiscard.add(i);
                            cardsToPlayByValue.get(value).add(i);
                        }
                    }
                }
            }
        }

        for (int i = 0; i < getHandSize(); i++) {
            if (!cardsToPlay.contains(i)) {
                cardsToDiscard.add(i);
            }
        }

        // On cherche les cartes que l'on connait déjà (une couleur et une valeur de même indice)
        knownCards.clear();
        for (CardColor color : CardColor.values()) {
            for (int i = 0; i < 5; i++) {
                if (cardsToPlayByColor.get(color).contains(i) && cardsToPlayByValue.get(i + 1).contains(i)) {
                    // On connait la carte
                    Card card = new Card(i + 1, color);
                    knownCards.put(card, i);
                    cardsToPlayByColor.get(color).remove(i);
                    cardsToPlayByValue.get(i + 1).remove(i);
                }
            }
        }

        // On vérifie si on peut jouer une carte que l'on connait
        for (Card card : knownCards.keySet()) {
            if (game.isPlayable(card)) {
                cardsToPlay.add(knownCards.get(card));
                cardsToDiscard.remove(knownCards.get(card));
            }
        }

        game.getHints().remove(this);

        System.out.println("IA " + getName() + " a les cartes à jouer suivantes : " + cardsToPlay);
        System.out.println("IA " + getName() + " a les cartes à défausser suivantes : " + cardsToDiscard); 
    }

    /**
     * Détermine si l'IA devrait défausser une carte.
     *
     * @param game L'état actuel du jeu.
     * @return Vrai si une carte doit être défaussée, faux sinon.
     */
    protected boolean shouldDiscardCard(Game game) {
        return !cardsToDiscard.isEmpty() && choosePlayerToGiveHint(game, false) == null;
    }

    /**
     * Détermine si l'IA devrait jouer une carte.
     *
     * @param game L'état actuel du jeu.
     * @return Vrai si une carte doit être jouée, faux sinon.
     */
    @Override   
    public boolean shouldPlayCard(Game game) {   
        return !cardsToPlay.isEmpty();
    }

    /**
     * Détermine si l'IA devrait donner un indice.
     *
     * @param game L'état actuel du jeu.
     * @return Vrai si un indice doit être donné, faux sinon.
     */
    @Override
    public boolean shouldGiveHint(Game game) {
        return game.haveBlueTokenAvailable() && !cardsToDiscard.isEmpty() && choosePlayerToGiveHint(game) != null;
    }

    /**
     * Choisit un joueur à qui donner un indice.
     *
     * @param game L'état actuel du jeu.
     * @return Le joueur choisi pour recevoir un indice.
     */
    @Override
    public Player choosePlayerToGiveHint(Game game) {
        // Le joueur avec le plus de cartes jouables
        Player player = null;
        int maxPlayableCards = 0;
        for (Player p : game.getPlayers()) {
            if (p == this)
                continue;
            int playableCards = 0;
            for (Card c : p.getHand().getCards()) {
                if (game.isPlayable(c)) {
                    playableCards++;
                }
            }
            if (playableCards > maxPlayableCards) {
                player = p;
                maxPlayableCards = playableCards;
            }
        }
        // Si aucun joueur n'a de carte jouable
        if (player == null) {
            // On choisit un joueur au hasard
            List<Player> players = new ArrayList<>(game.getPlayers());
            players.remove(this);
            player = players.get((int) (Math.random() * players.size()));
        }
        return player;
    }

    /**
     * Choisit un joueur à qui donner un indice, avec une option aléatoire.
     *
     * @param game L'état actuel du jeu.
     * @param alea Si vrai, choisit un joueur au hasard en cas d'égalité.
     * @return Le joueur choisi pour recevoir un indice.
     */
    public Player choosePlayerToGiveHint(Game game, boolean alea) {
        Player player = null;
        int maxPlayableCards = 0;
        for (Player p : game.getPlayers()) {
            if (p == this)
                continue;
            int playableCards = 0;
            for (Card c : p.getHand().getCards()) {
                if (game.isPlayable(c)) {
                    playableCards++;
                }
            }
            if (playableCards > maxPlayableCards) {
                player = p;
                maxPlayableCards = playableCards;
            }
        }
        if (player == null && alea) {
            List<Player> players = new ArrayList<>(game.getPlayers());
            players.remove(this);
            player = players.get((int) (Math.random() * players.size()));
        }
        return player;
    }

    /**
     * Joue une carte de la main du joueur.
     *
     * @param game L'état actuel du jeu.
     */
    @Override
    public void playCard(Game game) {
        int index = cardsToPlay.pollFirst();
        game.playCard(index);
        System.out.println("IA " + getName() + " joue la carte à la position " + (index + 1));
        for (int i = 0; i < cardsToPlay.size(); i++) {
            if (cardsToPlay.get(i) > index) {
                cardsToPlay.set(i, cardsToPlay.get(i) - 1);
            } else if (cardsToPlay.get(i) == index) {
                cardsToPlay.remove(i);
            }
        }
        Set<Integer> newCardsToDiscard = new HashSet<>();
        for (int i : cardsToDiscard) {
            if (i > index) {
                newCardsToDiscard.add(i - 1);
            } else if (i < index) {
                newCardsToDiscard.add(i);
            }
        }
        cardsToDiscard = newCardsToDiscard;
    }

    /**
     * Défausse une carte de la main du joueur.
     *
     * @param game L'état actuel du jeu.
     */
    @Override
    public void discardCard(Game game) {
        int index = cardsToDiscard.iterator().next();
        System.out.println("IA " + getName() + " défausse la carte à la position " + (index + 1));
        game.discardCard(index);
        cardsToDiscard.remove(index);
        // Crée un nouvel ensemble pour mettre à jour les positions des cartes restantes à défausser.
        Set<Integer> newCardsToDiscard = new HashSet<>();
        for (int i : cardsToDiscard) {
            if (i > index) {
                newCardsToDiscard.add(i - 1);
            } else if (i < index) {
                newCardsToDiscard.add(i);
            }
        }
        // Met à jour l'ensemble 'cardsToDiscard' avec les nouveaux indices.
        this.cardsToDiscard = newCardsToDiscard;
        // Met également à jour les positions des cartes à jouer.
        for (int i = 0; i < cardsToPlay.size(); i++) {
            if (cardsToPlay.get(i) > index) {
                cardsToPlay.set(i, cardsToPlay.get(i) - 1);
            } else if (cardsToPlay.get(i) == index) {
                cardsToPlay.remove(i);
            }
        }
        // Met à jour les positions des cartes à jouer par couleur.
        for (CardColor color : cardsToPlayByColor.keySet()) {
            Set<Integer> newCardsToPlay = new HashSet<>();
            for (int i : cardsToPlayByColor.get(color)) {
                if (i > index) {
                    newCardsToPlay.add(i - 1);
                } else if (i < index) {
                    newCardsToPlay.add(i);
                }
            }
            cardsToPlayByColor.put(color, newCardsToPlay);
        }
        // Met à jour les positions des cartes à jouer par valeur.
        for (int value : cardsToPlayByValue.keySet()) {
            Set<Integer> newCardsToPlay = new HashSet<>();
            for (int i : cardsToPlayByValue.get(value)) {
                if (i > index) {
                    newCardsToPlay.add(i - 1);
                } else if (i < index) {
                    newCardsToPlay.add(i);
                }
            }
            cardsToPlayByValue.put(value, newCardsToPlay);
        }
    }

    /**
     * Donne un indice à un joueur.
     *
     * @param game L'état actuel du jeu.
     */
    @Override
    public void giveHint(Game game) {
        Player player = choosePlayerToGiveHint(game);
        String hint = chooseHint(game, player);
        game.addHint(player, hint);
        System.out.println("IA " + getName() + " donne l'indice: << " + hint + " >> à " + player.getName());
        game.useBlueToken();
    }

    /**
     * Choisit un indice à donner à un joueur.
     *
     * @param game L'état actuel du jeu.
     * @param player Le joueur à qui donner l'indice.
     * @return L'indice choisi.
     */
    public String chooseHint(Game game, Player player) {
        // Vérifie les cartes jouables et donne un indice qui n'a pas encore été donné
        for (Card card : player.getHand().getCards()) {
            String valueHint = Hint.valueHint(card.getValue(), player.getHand());
            if (game.isPlayable(card) && !player.hasReceivedHint(valueHint)) {
                player.addReceivedHint(valueHint);
                return valueHint;
            }
        }
        // Sinon, choisit une carte au hasard qui n'a pas été indiquée avant
        List<Card> cards = new ArrayList<>(player.getHand().getCards());
        Collections.shuffle(cards);
        for (Card card : cards) {
            String colorHint = Hint.colorHint(card.getColor(), player.getHand());
            String valueHint = Hint.valueHint(card.getValue(), player.getHand());

            if (!player.hasReceivedHint(colorHint)) {
                player.addReceivedHint(colorHint);
                return colorHint;
            }
            if (!player.hasReceivedHint(valueHint)) {
                player.addReceivedHint(valueHint);
                return valueHint;
            }
        }

        // Sinon, retourne un indice aléatoire en dernier recours
        Card randomCard = cards.get((int) (Math.random() * cards.size()));
        String fallbackHint = Math.random() < 0.5 ? Hint.colorHint(randomCard.getColor(), player.getHand())
                                                 : Hint.valueHint(randomCard.getValue(), player.getHand());
        return fallbackHint;
    }

    /**
     * Choisit une carte à jouer.
     *
     * @param game L'état actuel du jeu.
     * @return La carte choisie à jouer.
     */
    @Override
    public Card chooseCardToPlay(Game game) {
        return null;
    }

    /**
     * Choisit une carte à défausser.
     *
     * @param game L'état actuel du jeu.
     * @return La carte choisie à défausser.
     */
    @Override
    public Card chooseCardToDiscard(Game game) {
        return null;
    }

    /**
     * Choisit un indice à donner.
     *
     * @param game L'état actuel du jeu.
     * @return L'indice choisi.
     */
    @Override
    public String chooseHint(Game game) {
        return null;
    }
}
