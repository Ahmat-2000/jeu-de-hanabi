package controller;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import model.Deck;
import model.Game;
import model.Hand;
import model.Player;
import model.strategies.AIAdvancedPlayer;
import model.strategies.AIMalinPlayer;
import view.BodyPanel;
import view.CustomButton;
import view.FooterPanel;
import view.GUI;
import view.HeaderPanel;

/**
 * HomeController class manages the home screen of the application,
 * handles user inputs for AI players, and starts the game.
 */
public class HomeController {
    /**
     * Le panneau principal de l'interface graphique.
     */
    private JPanel panelFram;

    /**
     * L'instance du jeu.
     */
    private Game game;

    /**
     * Le bouton pour démarrer le jeu.
     */
    private CustomButton start;

    /**
     * L'étiquette pour les joueurs AIMalin.
     */
    private JLabel malinLabel;

    /**
     * L'étiquette pour les joueurs AIAdvanced.
     */
    private JLabel advancedLabel;

    /**
     * L'étiquette pour afficher les messages d'erreur.
     */
    private JLabel errorLabel;

    /**
     * Le champ de texte pour entrer le nombre de joueurs AIMalin.
     */
    private JTextField malinNumber;

    /**
     * Le champ de texte pour entrer le nombre de joueurs AIAdvanced.
     */
    private JTextField advancedNumber;

    /**
     * Constructor for HomeController.
     * 
     * @param window the GUI window of the application
     */
    public HomeController(GUI window) {
        panelFram = window.getPanelFram();
        homeWindow();
    }

    /**
     * Gets the main panel frame.
     * 
     * @return the main panel frame
     */
    public JPanel getPanelFram() {
        return this.panelFram;
    }

    /**
     * Sets up the home window with user input fields and a start button.
     */
    public void homeWindow() {
        panelFram.setLayout(null);

        malinLabel = new JLabel("Entrez le nombre de joueurs AIMalin (0 à 4)");
        malinLabel.setFont(new Font("Serif", Font.BOLD, 14));
        malinNumber = new JTextField(1);

        advancedLabel = new JLabel("Entrez le nombre de joueurs AIAdvanced (0 à 4)");
        advancedLabel.setFont(new Font("Serif", Font.BOLD, 14));
        advancedNumber = new JTextField(1);

        errorLabel = new JLabel();
        errorLabel.setFont(new Font("Serif", Font.BOLD, 14));
        errorLabel.setForeground(Color.RED);

        start = new CustomButton("Démarrer le jeu");
        
        malinLabel.setBounds(260, 330, 600, 40);
        malinNumber.setBounds(260, 370, 600, 40);
        advancedLabel.setBounds(260, 410, 600, 40);
        advancedNumber.setBounds(260, 450, 600, 40);
        errorLabel.setBounds(300, 490, 600, 40);
        start.setBounds(460, 530, 200, 40);

        panelFram.add(malinLabel);
        panelFram.add(malinNumber);
        panelFram.add(advancedLabel);
        panelFram.add(advancedNumber);
        panelFram.add(errorLabel);
        panelFram.add(start);

        panelFram.validate();
        textFieldListener();
        buttonListener();
    }

    /**
     * Adds a key listener to the text fields to ensure only numeric input.
     */
    private void textFieldListener() {
        KeyAdapter keyAdapter = new KeyAdapter() {
            public void keyPressed(KeyEvent ke) {
                JTextField source = (JTextField) ke.getSource();
                String value = source.getText();
                int l = value.length();
                if (ke.getKeyChar() >= '0' && ke.getKeyChar() <= '9' && l <= 1) {
                    source.setEditable(true);
                    errorLabel.setText("");
                } else {
                    errorLabel.setText("* Entrez uniquement des chiffres numériques.");
                }
            }
        };
        malinNumber.addKeyListener(keyAdapter);
        advancedNumber.addKeyListener(keyAdapter);
    }

    /**
     * Adds a button listener to handle the start button click event.
     */
    private void buttonListener() {
        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int malinPlayers = Integer.parseInt(malinNumber.getText());
                    int advancedPlayers = Integer.parseInt(advancedNumber.getText());
                    int totalPlayers = malinPlayers + advancedPlayers;

                    if (totalPlayers < 1 || totalPlayers > 4) {
                        errorLabel.setText("Le nombre total de IA doit être entre 1 et 4.");
                        return;
                    }

                    HomeController.this.panelFram.removeAll();
                    HomeController.this.panelFram.updateUI();
                    HomeController.this.startGame(totalPlayers, malinPlayers, advancedPlayers);

                } catch (NumberFormatException ex) {
                    errorLabel.setText("Veuillez entrer des nombres valides.");
                }
            }
        });
    }

    /**
     * Starts the game with the specified number of players.
     * 
     * @param totalPlayers the total number of players
     * @param malinPlayers the number of AIMalin players
     * @param advancedPlayers the number of AIAdvanced players
     */
    private void startGame(int totalPlayers, int malinPlayers, int advancedPlayers) {
        Deck deck = new Deck();
        List<Player> players = new ArrayList<>();
        int nbCards = totalPlayers > 3 ? 4 : 5;
        players.add(new Player("Human", new Hand(deck, nbCards))); // Adding the human player

        // Adding AIMalin players
        for (int i = 1; i <= malinPlayers; i++) {
            players.add(new AIMalinPlayer("AIMalin" + i, new Hand(deck, nbCards)));
        }

        // Adding AIAdvanced players
        for (int i = 1; i <= advancedPlayers; i++) {
            players.add(new AIAdvancedPlayer("AdvancedAI" + i, new Hand(deck, nbCards)));
        }

        this.game = new Game(players, deck);
        panelFram.setLayout(new BorderLayout(10, 10));
        HeaderPanel header = new HeaderPanel(game, this);
        BodyPanel body = new BodyPanel(game);
        FooterPanel footer = new FooterPanel(game);
        panelFram.add(header, BorderLayout.NORTH);
        panelFram.add(body, BorderLayout.CENTER);
        panelFram.add(footer, BorderLayout.SOUTH);
    }

    /**
     * Initializes the home screen components and listeners.
     */
    public void home() {
        textFieldListener();
        buttonListener();
    }
}
