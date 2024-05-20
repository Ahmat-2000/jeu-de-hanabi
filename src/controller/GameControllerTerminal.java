package controller;

import model.*;
import model.strategies.AIAdvancedPlayer;
import model.strategies.AIMalinPlayer;
import model.strategies.AIPlayer;
import view.*;

import java.util.*;

/**
 * Contrôleur pour gérer le jeu en mode terminal.
 */
public class GameControllerTerminal {

    /**
     * Le modèle du jeu.
     */
    private Game model;

    /**
     * La vue du jeu.
     */
    private final GameView view;

    /**
     * Scanner pour lire les entrées de l'utilisateur.
     */
    private final Scanner input;

    /**
     * Constructeur pour le contrôleur de jeu en mode terminal.
     * Initialise la vue, le scanner et prépare le jeu.
     */
    public GameControllerTerminal() {
        this.view = new GameTerminalView(this);
        this.input = new Scanner(System.in);
        prepareGame();
    }

    /**
     * Obtient le modèle du jeu.
     *
     * @return Le modèle du jeu.
     */
    public Game getModel() {
        return model;
    }

    /**
     * Obtient la vue du jeu.
     *
     * @return La vue du jeu.
     */
    public GameView getView() {
        return view;
    }

    /**
     * Affiche l'état actuel du jeu.
     */
    public void showGame() {
        view.showGame();
    }

    /**
     * Demande au joueur actuel quel type d'action il souhaite effectuer et l'exécute.
     */
    public void askPlayType() {
        int playType = view.askPlayType(model.getActualPlayer());

        if (playType == 1) {
            int index = view.askCard(model.getActualPlayer());
            model.playCard(index);
        } else if (playType == 2) {
            int index = view.askCard(model.getActualPlayer());
            model.discardCard(index);
        } else if (playType == 3) {
            Map<Player, String> hint = view.askHint(model.getActualPlayer());
            Player playerWithHint = hint.keySet().iterator().next();
            model.addHint(playerWithHint, hint.get(playerWithHint));
            model.useBlueToken();
        }
    }

    /**
     * Configure une nouvelle partie de jeu.
     * Crée les joueurs (humains et IA), distribue les cartes, et initialise l'état du jeu.
     */
    private void prepareGame() {
        Deck deck = new Deck();
        int[] playersSetup = view.askPlayersSetup();
        int numberPlayers = playersSetup[0];
        int numberAIPlayers = playersSetup[1];
        int numberAIMalinPlayers = playersSetup[2];
        int numberAIAdvancedPlayers = playersSetup[3];

        List<Player> players = new ArrayList<>();
        int nbCards = numberPlayers > 3 ? 4 : 5;

        for (int i = 0; i < numberPlayers; i++) {
            Hand hand = new Hand(deck, nbCards);
            if (i < numberAIPlayers) {
                if (i < numberAIMalinPlayers) {
                    // Création d'un AIMalinPlayer
                    players.add(new AIMalinPlayer("AIMalinPlayer " + (i + 1), hand));
                } else if (i < numberAIMalinPlayers + numberAIAdvancedPlayers) {
                    // Création d'un AIAdvancedPlayer
                    players.add(new AIAdvancedPlayer("AIAdvancedPlayer " + (i + 1), hand));
                } else {
                    // Création d'un AIPlayer standard
                    players.add(new AIPlayer("AI Player " + (i + 1), hand));
                }
            } else {
                // Création d'un joueur humain
                players.add(new Player("Human Player " + (i + 1), hand));
            }
        }

        this.model = new Game(players, deck);
    }

    /**
     * Passe au joueur suivant.
     */
    public void nextPlayer() {
        model.changeActualPlayer();
    }

    /**
     * Démarre la boucle de jeu en mode terminal.
     * Continue jusqu'à ce que le jeu soit terminé.
     */
    public void play() {
        while (!model.isGameOver()) {
            Player currentPlayer = model.getActualPlayer();
            System.out.println("C'est le tour de : " + currentPlayer.getName()); // Afficher le joueur actuel
            showGame();
            if (currentPlayer instanceof AIPlayer) {
                ((AIPlayer) currentPlayer).chooseAction(model);
                System.out.println(currentPlayer.getName() + " (IA) a fini son tour."); // Confirmation que l'IA a joué
            } else {
                askPlayType();
            }
            nextPlayer();
        }
        int finalScore = model.getScore();
        System.out.println("Le jeu est terminé.\nVous avez un score de " + finalScore + " points."); // Afficher le score final
        System.out.println(model.getScoreFeedback());
    }

    /**
     * Ferme le scanner d'entrée et affiche un message de fin.
     */
    public void close() {
        input.close();
        System.out.println("Merci d'avoir joué ! Fermeture du jeu...");
    }

    /**
     * Vérifie si un indice peut être donné (s'il reste des jetons bleus).
     *
     * @return Vrai s'il reste des jetons bleus, faux sinon.
     */
    public boolean canGiveHint() {
        return model.haveBlueTokenAvailable();
    }
}
