package model.strategies;

import model.*;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * Classe représentant un joueur IA dans le jeu Hanabi.
 * Cette classe étend la classe Player et implémente l'interface IA.
 */
public class AIPlayer extends Player implements IA {

    /**
     * Une instance de Random pour générer des nombres aléatoires.
     */
    private final Random random = new Random();

    /**
     * Constructeur pour créer un joueur IA.
     *
     * @param name Le nom du joueur IA.
     * @param hand La main de cartes du joueur IA.
     */
    public AIPlayer(String name, Hand hand) {
        super(name, hand);
    }

    /**
     * Détermine et exécute une action pour le joueur IA en fonction des règles du jeu.
     *
     * @param game Le jeu en cours.
     */
    public void chooseAction(Game game) {        
        int action;
        do {
            action = random.nextInt(3) + 1; // Retourne un nombre aléatoire entre 1 et 3
        } while (action == 3 && !game.haveBlueTokenAvailable());  // Donner un indice est seulement possible si des jetons bleus sont disponibles

        // Exécute l'action choisie
        switch (action) {
            case 1: 
                playCard(game);
                break;
            case 2: 
                discardCard(game);
                break;
            case 3: 
                giveHint(game);
                break;
            default:
                System.out.println(this.getName() + " (IA) a choisi une action non valide.");
                break;
        }
    }

    /**
     * Donne un indice à un autre joueur.
     *
     * @param game Le jeu en cours.
     */
    public void giveHint(Game game) {
        List<Player> otherPlayers = game.getPlayers().stream() // Récupère la liste des autres joueurs et les convertit en flux
                                         .filter(p -> !p.equals(this)) // Exclut l'IA elle-même (this)
                                         .collect(Collectors.toList()); // Convertit le flux filtré en une liste
        if (!otherPlayers.isEmpty()) { // Vérifie s'il y a d'autres joueurs à qui l'IA peut potentiellement donner un indice
            // Sélection aléatoire d'un joueur pour recevoir l'indice
            Player playerToHint = otherPlayers.get(random.nextInt(otherPlayers.size()));
            String hint = getRandomHint(playerToHint);
            System.out.println(this.getName() + " (IA) décide de donner un indice à " + playerToHint.getName() + ": " + hint);
            game.addHint(playerToHint, hint);
        } else {
            System.out.println(this.getName() + " (IA) ne peut pas donner d'indice car il n'y a pas d'autres joueurs.");
        }
    }

    /**
     * Défausse une carte aléatoirement.
     *
     * @param game Le jeu en cours.
     */
    @Override
    public void discardCard(Game game) {
        int cardToDiscardIndex = random.nextInt(this.getHandSize()); // Sélection aléatoire d'une carte à défausser
        System.out.println(this.getName() + " (IA) décide de défausser la carte à l'index " + cardToDiscardIndex);
        game.discardCard(this.getHand().getCards().get(cardToDiscardIndex));
    }

    /**
     * Joue une carte aléatoirement.
     *
     * @param game Le jeu en cours.
     */
    @Override
    public void playCard(Game game) {
        List<Card> hand = game.getActualPlayer().getHand().getCards();
        int cardToPlayIndex = random.nextInt(hand.size()); // Sélection aléatoire d'une carte à jouer
        System.out.println(this.getName() + " (IA) décide de jouer la carte à l'index " + cardToPlayIndex);
        game.playCard(hand.get(cardToPlayIndex));
    }

    /**
     * Génère un indice aléatoire pour un autre joueur.
     *
     * @param otherPlayer Le joueur qui reçoit l'indice.
     * @return Une chaîne de caractères représentant l'indice donné.
     */
    protected String getRandomHint(Player otherPlayer) {
        // L'indice est soit une couleur, soit une valeur
        CardColor[] colors = CardColor.values(); // Récupère toutes les valeurs possibles de l'énumération des couleurs des cartes
        CardColor colorHint = colors[random.nextInt(colors.length)]; // Sélection aléatoire d'une couleur

        return random.nextBoolean() ? Hint.colorHint(colorHint, otherPlayer.getHand()) : Hint.valueHint(random.nextInt(5) + 1, otherPlayer.getHand());
    }

    @Override
    public boolean shouldPlayCard(Game game) {
        throw new UnsupportedOperationException("Unimplemented method 'shouldPlayCard'");
    }

    @Override
    public boolean shouldGiveHint(Game game) {
        throw new UnsupportedOperationException("Unimplemented method 'shouldGiveHint'");
    }

    @Override
    public Card chooseCardToPlay(Game game) {
        throw new UnsupportedOperationException("Unimplemented method 'chooseCardToPlay'");
    }

    @Override
    public Card chooseCardToDiscard(Game game) {
        throw new UnsupportedOperationException("Unimplemented method 'chooseCardToDiscard'");
    }

    @Override
    public Player choosePlayerToGiveHint(Game game) {
        throw new UnsupportedOperationException("Unimplemented method 'choosePlayerToGiveHint'");
    }

    @Override
    public String chooseHint(Game game) {
        throw new UnsupportedOperationException("Unimplemented method 'chooseHint'");
    }
}
