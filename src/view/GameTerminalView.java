package view;

import controller.GameControllerTerminal;
import model.*;

import java.util.Map;
import java.util.Scanner;

/**
 * GameTerminalView est une classe qui implémente l'interface GameView.
 * Elle fournit une vue en terminal pour le jeu Hanabi.
 */
public class GameTerminalView implements GameView {

    /**
     * Le contrôleur du jeu en mode terminal.
     */
    private final GameControllerTerminal controller;

    /**
     * Scanner pour lire les entrées de l'utilisateur.
     */
    private final Scanner input = new Scanner(System.in);

    /**
     * Constructeur pour créer une GameTerminalView avec le contrôleur spécifié.
     *
     * @param controller Le contrôleur du jeu en mode terminal.
     */
    public GameTerminalView(GameControllerTerminal controller) {
        this.controller = controller;
    }

    @Override
    /**
     * Affiche l'état actuel du jeu dans le terminal.
     */
    public void showGame() {
        // On nettoie la console
        System.out.println("\033[H\033[2J");
        // On affiche le board
        System.out.println(controller.getModel());
    }

    @Override
    /**
     * Demande à l'utilisateur de configurer les joueurs.
     *
     * @return int[] Un tableau contenant le nombre total de joueurs, le nombre de joueurs IA standard,
     *         le nombre de AIMalinPlayers et le nombre de AIAdvancedPlayers.
     */
    public int[] askPlayersSetup() {
        System.out.println("Combien de joueurs au total ? (2-5)");
        int numberPlayers = input.nextInt();

        // Vérification pour le nombre total de joueurs
        while (numberPlayers < 2 || numberPlayers > 5) {
            System.out.println("Le nombre de joueurs doit être entre 2 et 5. Combien de joueurs au total ?");
            numberPlayers = input.nextInt();
        }

        System.out.println("Combien de ces joueurs sont des IA standard ? (0-" + numberPlayers + ")");
        int numberAIPlayers = input.nextInt();

        // Vérification pour le nombre de joueurs IA
        while (numberAIPlayers < 0 || numberAIPlayers > numberPlayers) {
            System.out.println("Le nombre de joueurs IA doit être entre 0 et " + numberPlayers + ". Combien de ces joueurs sont des IA standard ?");
            numberAIPlayers = input.nextInt();
        }

        int numberAIMalinPlayers = 0;
        int numberAIAdvancedPlayers = 0;
        if (numberAIPlayers > 0) {
            System.out.println("Parmi les joueurs IA, combien sont des AIMalinPlayer ? (0-" + numberAIPlayers + ")");
            numberAIMalinPlayers = input.nextInt();

            // Vérification pour le nombre de AIMalinPlayer
            while (numberAIMalinPlayers < 0 || numberAIMalinPlayers > numberAIPlayers) {
                System.out.println("Le nombre de AIMalinPlayer doit être entre 0 et " + numberAIPlayers + ". Combien sont des AIMalinPlayer ?");
                numberAIMalinPlayers = input.nextInt();
            }

            if (numberAIPlayers - numberAIMalinPlayers > 0) {
                System.out.println("Combien des joueurs IA restants sont des AIAdvancedPlayer ? (0-" + (numberAIPlayers - numberAIMalinPlayers) + ")");
                numberAIAdvancedPlayers = input.nextInt();

                // Vérification pour le nombre de AIAdvancedPlayer
                while (numberAIAdvancedPlayers < 0 || numberAIAdvancedPlayers > (numberAIPlayers - numberAIMalinPlayers)) {
                    System.out.println("Le nombre de AIAdvancedPlayer doit être entre 0 et " + (numberAIPlayers - numberAIMalinPlayers) + ". Combien sont des AIAdvancedPlayer ?");
                    numberAIAdvancedPlayers = input.nextInt();
                }
            }
        }

        input.nextLine();

        System.out.println("Vous avez choisi " + numberPlayers + " joueurs au total, dont " + numberAIPlayers + " IA, y compris " + numberAIMalinPlayers + " AIMalinPlayer et " + numberAIAdvancedPlayers + " AIAdvancedPlayer.");
        return new int[]{numberPlayers, numberAIPlayers, numberAIMalinPlayers, numberAIAdvancedPlayers};
    }

    @Override
    /**
     * Demande au joueur quelle action il souhaite effectuer.
     *
     * @param player Le joueur actuel.
     * @return int Le type d'action choisie (1 pour jouer, 2 pour défausser, 3 pour donner un indice).
     */
    public int askPlayType(Player player) {
        System.out.println("Joueur " + player.getName() + " que voulez vous faire ?");
        System.out.println("1 - Jouer une carte");
        System.out.println("2 - Défausser une carte");
        System.out.println("3 - Donner un indice");

        int playType = input.nextInt();

        // Vérification de la saisie
        while (playType < 1 || playType > 3) {
            System.out.println("Veuillez saisir un nombre entre 1 et 3");
            playType = input.nextInt();
        }

        boolean print = true;
        // Vérifier si le joueur peut donner un indice (jeton bleu disponible)
        if (playType == 3 && !controller.canGiveHint()) {
            System.out.println("Vous n'avez plus le droit de donner un indice");
            playType = askPlayType(player);
            print = false;
        }

        if (print) {
            System.out.println("Vous avez choisi la règle " + playType);
        }

        input.nextLine();
        return playType;
    }

    @Override
    /**
     * Demande au joueur quel indice il souhaite donner et à quel joueur.
     *
     * @param player Le joueur actuel.
     * @return Map<Player, String> Une map contenant le joueur cible et l'indice donné.
     */
    public Map<Player, String> askHint(Player player) {
        System.out.println("Voici les cartes de chaque joueur :");
        int i = 0;
        Player playerWhoGetsHint = null;
        for (Player p : controller.getModel().getPlayers()) {
            if (!p.equals(player)) {
                System.out.println(i + " - " + p.getName() + " : " + p.getHand().toString());
                i++;
            }
        }
        System.out.println("Quel joueur voulez-vous aider ?");
        int playerIndex = input.nextInt() - 1;
        input.nextLine();

        // Vérification de la saisie
        while (playerIndex < 0 || playerIndex >= controller.getModel().getPlayers().size() || controller.getModel().getPlayers().get(playerIndex).equals(player)) {
            System.out.println("Veuillez saisir un nombre valide et différent de ce numéro.");
            playerIndex = input.nextInt() - 1;
            input.nextLine();
        }

        playerWhoGetsHint = controller.getModel().getPlayers().get(playerIndex);
        System.out.println("Vous avez choisi d'aider le joueur " + playerWhoGetsHint.getName());
        System.out.println("Tapez: \n  1: pour donner un indice sur la couleur\n  2: pour la valeur");
        int hintType = input.nextInt();
        input.nextLine();

        // Vérification de la saisie
        while (hintType < 1 || hintType > 2) {
            System.out.println("Veuillez saisir 1 ou 2.");
            hintType = input.nextInt();
            input.nextLine();
        }

        if (hintType == 1) {
            // Afficher les options de couleur
            System.out.println("Quelle couleur voulez-vous donner ?");
            System.out.println("1 - RED");
            System.out.println("2 - BLUE");
            System.out.println("3 - YELLOW");
            System.out.println("4 - GREEN");
            System.out.println("5 - WHITE");
            int colorIndex = input.nextInt();
            input.nextLine();

            // Vérification de la saisie
            while (colorIndex < 1 || colorIndex > 5) {
                System.out.println("Veuillez choisir un numéro valide le choix est compris entre 1 et 5.");
                colorIndex = input.nextInt();
                input.nextLine();
            }

            CardColor color = convertChoiceToColor(colorIndex);
            if (handContainsColor(playerWhoGetsHint.getHand(), color)) {
                return Map.of(playerWhoGetsHint, Hint.colorHint(color, playerWhoGetsHint.getHand()));
            } else {
                System.out.println("Coup non valide. Cette couleur n'est pas dans la main du joueur.");
                return askHint(player); // Demandez à nouveau un indice
            }
        } else {
            System.out.println("Quelle valeur voulez-vous indiquer ?");
            int value = input.nextInt();
            input.nextLine();

            // Vérification de la valeur choisie
            if (value >= 1 && value <= 5 && handContainsValue(playerWhoGetsHint.getHand(), value)) {
                return Map.of(playerWhoGetsHint, Hint.valueHint(value, playerWhoGetsHint.getHand()));
            } else {
                System.out.println("Coup non valide. Cette valeur n'est pas dans la main du joueur.");
                return askHint(player); // Demandez à nouveau un indice
            }
        }
    }

    /**
     * Convertit le choix de l'utilisateur en couleur de carte.
     *
     * @param choice L'index du choix.
     * @return CardColor La couleur de carte correspondante.
     */
    private CardColor convertChoiceToColor(int choice) {
        switch (choice) {
            case 1: return CardColor.RED;
            case 2: return CardColor.BLUE;
            case 3: return CardColor.YELLOW;
            case 4: return CardColor.GREEN;
            case 5: return CardColor.WHITE;
            default: return null; // gestion des choix non valides
        }
    }

    /**
     * Vérifie si la main contient une carte de la couleur spécifiée.
     *
     * @param hand La main du joueur.
     * @param color La couleur à vérifier.
     * @return boolean Vrai si la main contient la couleur, faux sinon.
     */
    private boolean handContainsColor(Hand hand, CardColor color) {
        return hand.getCards().stream().anyMatch(card -> card.getColor().equals(color));
    }

    /**
     * Vérifie si la main contient une carte de la valeur spécifiée.
     *
     * @param hand La main du joueur.
     * @param value La valeur à vérifier.
     * @return boolean Vrai si la main contient la valeur, faux sinon.
     */
    private boolean handContainsValue(Hand hand, int value) {
        return hand.getCards().stream().anyMatch(card -> card.getValue() == value);
    }

    @Override
    /**
     * Demande au joueur quelle carte il souhaite jouer.
     *
     * @param player Le joueur actuel.
     * @return int L'index de la carte choisie.
     */
    public int askCard(Player player) {
        System.out.println(player.getName() + " vous avez " + player.getHand().getSize() + " cartes en main");
        System.out.println("Quel est l'index de la carte que vous voulez jouer ?(1-" + player.getHand().getSize() + ")");
        int index = input.nextInt();

        // Vérification de la saisie
        while (index < 1 || index > player.getHand().getSize()) {
            System.out.println("Veuillez saisir un nombre entre 1 et " + player.getHand().getSize());
            index = input.nextInt();
        }

        System.out.println("Vous avez choisi de jouer la carte " + player.getHand().getSize());

        input.nextLine();
        return index - 1;
    }

    @Override
    /**
     * Méthode non implémentée pour demander quel joueur est ciblé.
     *
     * @param player Le joueur actuel.
     * @return Rien, car non implémentée.
     */
    public int askPlayer(Player player) {
        throw new UnsupportedOperationException("Unimplemented method 'askPlayer'");
    }
}
