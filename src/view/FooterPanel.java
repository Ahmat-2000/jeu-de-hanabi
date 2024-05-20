package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import model.*;
import model.strategies.IA;

/**
 * FooterPanel est un JPanel personnalisé qui contient les boutons d'action pour le jeu,
 * permettant de jouer, de défausser des cartes et de donner des indices.
 */
public class FooterPanel extends JPanel {
    /**
     * Le bouton pour jouer une carte.
     */
    protected CustomButton playButton;

    /**
     * Le bouton pour défausser une carte.
     */
    protected CustomButton discardButton;

    /**
     * Le bouton pour donner un indice.
     */
    protected CustomButton giveHintButton;

    /**
     * Indique si une carte est distribuée.
     */
    protected boolean isDeal;

    /**
     * Le modèle du jeu.
     */
    protected Game game;

    /**
     * Constructeur pour créer un FooterPanel avec le modèle de jeu spécifié.
     *
     * @param game Le modèle de jeu.
     */
    public FooterPanel(Game game) {
        this.game = game;
        setLayout(new FlowLayout(FlowLayout.CENTER, 20, 5));
        giveHintButton = new CustomButton("Give Hint");
        playButton = new CustomButton("Play");
        discardButton = new CustomButton("Discard");
        add(playButton);
        add(discardButton);
        add(giveHintButton);
        this.discardButton();
        this.playButton();
        this.giveHintButton();
    }

    /**
     * Ajoute un écouteur d'action au bouton de défausse.
     * Permet de défausser une carte.
     */
    public void discardButton() {
        discardButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (game.isGameOver()) {
                    showGameOverDialog();
                    return;
                }
                
                String[] options = {"1", "2", "3", "4", "5"};
                String choice = (String) JOptionPane.showInputDialog(
                        FooterPanel.this.getParent(),
                        "Choose a card index to discard:",
                        "Discard Card",
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        options,
                        options[0]);

                if (choice != null) {
                    int index = Integer.parseInt(choice) - 1;
                    game.discardCard(index);
                    game.changeActualPlayer();
                    for (int i = 1; i < game.getNbPlayers(); i++) {
                        if (game.isGameOver()) {
                            showGameOverDialog();
                            return;
                        }
                        IA player = (IA) game.getActualPlayer();
                        player.chooseAction(game);
                        game.changeActualPlayer();
                    }
                }
                
                if (game.isGameOver()) {
                    showGameOverDialog();
                }
            }
        });
    }

    /**
     * Ajoute un écouteur d'action au bouton de jeu.
     * Permet de jouer une carte.
     */
    public void playButton() {
        playButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (game.isGameOver()) {
                    showGameOverDialog();
                    return;
                }

                String[] options = {"1", "2", "3", "4", "5"};
                String choice = (String) JOptionPane.showInputDialog(
                        FooterPanel.this.getParent(),
                        "Choose a card index to play:",
                        "Play Card",
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        options,
                        options[0]);

                if (choice != null) {
                    int index = Integer.parseInt(choice) - 1;
                    game.playCard(index);
                    game.changeActualPlayer();
                    for (int i = 1; i < game.getNbPlayers(); i++) {
                        if (game.isGameOver()) {
                            showGameOverDialog();
                            return;
                        }
                        IA player = (IA) game.getActualPlayer();
                        player.chooseAction(game);
                        game.changeActualPlayer();
                    }
                }

                if (game.isGameOver()) {
                    showGameOverDialog();
                }
            }
        });
    }

    /**
     * Ajoute un écouteur d'action au bouton d'indice.
     * Permet de donner un indice à un autre joueur.
     */
    public void giveHintButton() {
        giveHintButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (game.isGameOver()) {
                    showGameOverDialog();
                    return;
                }
                if (canGiveHint()) {
                    
                    Player targetPlayer = chooseTargetPlayer();
                    if (targetPlayer != null) {
                        String[] hintOptions = {"Color", "Value"};
                        String hintChoice = (String) JOptionPane.showInputDialog(
                                FooterPanel.this.getParent(),
                                "Choose hint type:",
                                "Give Hint",
                                JOptionPane.QUESTION_MESSAGE,
                                null,
                                hintOptions,
                                hintOptions[0]);
        
                        if (hintChoice != null) {
                            if (hintChoice.equals("Color")) {
                                String[] colorOptions = {"Red", "Yellow", "Green", "Blue", "White"};
                                ArrayList<String> colorList = new ArrayList<>(Arrays.asList("Red", "Yellow", "Green", "Blue", "White"));
                                
                                String colorChoice = (String) JOptionPane.showInputDialog(
                                        FooterPanel.this.getParent(),
                                        "Choose a color to hint:",
                                        "Give Hint",
                                        JOptionPane.QUESTION_MESSAGE,
                                        null,
                                        colorOptions,
                                        colorOptions[0]);
        
                                if (colorChoice != null) {
                                    CardColor color = convertChoiceToColor(colorList.indexOf(colorChoice));
                                    if (handContainsColor(targetPlayer.getHand(), color)) {
                                        Map<Player, String> hint = Map.of(targetPlayer, Hint.colorHint(color, targetPlayer.getHand()));
                                        Player playerWithHint = hint.keySet().iterator().next();
                                        game.addHint(playerWithHint, hint.get(playerWithHint));
                                        game.useBlueToken();
                                        game.changeActualPlayer();
                                        for (int i = 1; i < game.getNbPlayers(); i++) {
                                            if (game.isGameOver()) {
                                                showGameOverDialog();
                                                return;
                                            }
                                            IA player = (IA) game.getActualPlayer();
                                            player.chooseAction(game);
                                            game.changeActualPlayer();
                                        }
                                    } else {
                                        System.out.println("Coup non valide. Cette couleur n'est pas dans la main du joueur.");
                                    }
                                }
                            } else if (hintChoice.equals("Value")) {
                                String[] valueOptions = {"1", "2", "3", "4", "5"};
                                String valueChoice = (String) JOptionPane.showInputDialog(
                                        FooterPanel.this.getParent(),
                                        "Choose a value to hint:",
                                        "Give Hint",
                                        JOptionPane.QUESTION_MESSAGE,
                                        null,
                                        valueOptions,
                                        valueOptions[0]);
                                if (valueChoice != null) {
                                    int value = Integer.parseInt(valueChoice);
                                    if (value >= 1 && value <= 5 && handContainsValue(targetPlayer.getHand(), value)) {
                                        Map<Player, String> hint = Map.of(targetPlayer, Hint.valueHint(value, targetPlayer.getHand()));
                                        Player playerWithHint = hint.keySet().iterator().next();
                                        game.addHint(playerWithHint, hint.get(playerWithHint));
                                        game.useBlueToken();
                                        game.changeActualPlayer();
                                        for (int i = 1; i < game.getNbPlayers(); i++) {
                                            if (game.isGameOver()) {
                                                showGameOverDialog();
                                                return;
                                            }
                                            IA player = (IA) game.getActualPlayer();
                                            player.chooseAction(game);
                                            game.changeActualPlayer();
                                        }
                                    } else {
                                        System.out.println("Coup non valide. Cette valeur n'est pas dans la main du joueur.");
                                    }
                                }
                            }
                        }
                    }
        
                    if (game.isGameOver()) {
                        showGameOverDialog();
                    }
                }
            }
        });
    }

    /**
     * Permet de choisir un joueur cible pour donner un indice.
     *
     * @return Le joueur cible sélectionné.
     */
    private Player chooseTargetPlayer() {
        Player[] players = game.getPlayers().toArray(new Player[0]);
        String[] playerNames = new String[players.length];
    
        for (int i = 1; i < players.length; i++) {
            playerNames[i] = players[i].getName(); 
        }
    
        String selectedPlayerName = (String) JOptionPane.showInputDialog(
                FooterPanel.this.getParent(),
                "Choose a player to give hint:",
                "Select Player",
                JOptionPane.QUESTION_MESSAGE,
                null,
                playerNames,
                playerNames[1]);
    
        if (selectedPlayerName != null) {
            for (Player player : players) {
                if (player.getName().equals(selectedPlayerName)) {
                    return player;
                }
            }
        }
    
        return null;
    }

    /**
     * Affiche une boîte de dialogue indiquant la fin du jeu et le score final.
     */
    private void showGameOverDialog() {
        JOptionPane.showMessageDialog(
            FooterPanel.this.getParent(),
            "Vous avez un score de " + game.getScore() + " points.\n" + game.getScoreFeedback(),
            "Le jeu est terminé",
            JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Convertit le choix de l'utilisateur en couleur de carte.
     *
     * @param choice L'index du choix.
     * @return La couleur de carte correspondante.
     */
    private CardColor convertChoiceToColor(int choice) {
        switch (choice) {
            case 0: return CardColor.RED;
            case 1: return CardColor.YELLOW;
            case 2: return CardColor.GREEN;
            case 3: return CardColor.BLUE;
            case 4: return CardColor.WHITE;
            default: return null; // gestion des choix non valides
        }
    }

    /**
     * Vérifie si la main contient une carte de la couleur spécifiée.
     *
     * @param hand La main du joueur.
     * @param color La couleur à vérifier.
     * @return Vrai si la main contient la couleur, faux sinon.
     */
    private boolean handContainsColor(Hand hand, CardColor color) {
        return hand.getCards().stream().anyMatch(card -> card.getColor().equals(color));
    }

    /**
     * Vérifie si la main contient une carte de la valeur spécifiée.
     *
     * @param hand La main du joueur.
     * @param value La valeur à vérifier.
     * @return Vrai si la main contient la valeur, faux sinon.
     */
    private boolean handContainsValue(Hand hand, int value) {
        return hand.getCards().stream().anyMatch(card -> card.getValue() == value);
    }

    /**
     * Vérifie si un indice peut être donné (s'il reste des jetons bleus).
     *
     * @return Vrai s'il reste des jetons bleus, faux sinon.
     */
    public boolean canGiveHint() {
        return game.haveBlueTokenAvailable();
    }
}
