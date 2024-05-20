package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;

import controller.HomeController;
import model.Game;
import model.Deck;

/**
 * HeaderPanel est un JPanel personnalisé qui affiche la vue du deck, des jetons et un bouton Home.
 */
public class HeaderPanel extends JPanel {
    /**
     * Le deck du jeu.
     */
    private Deck deck;

    /**
     * Constructeur pour créer un HeaderPanel avec le modèle de jeu spécifié.
     *
     * @param game  Le modèle de jeu.
     * @param homeControl Le contrôleur de la page d'accueil.
     */
    public HeaderPanel(Game game, HomeController homeControl) {
        this.deck = game.getDeck();
        setLayout(new BorderLayout());
        
        // Ajoute la vue du deck dans la partie ouest du panneau
        add(new DeckView(this.deck), BorderLayout.WEST);
        
        // Crée un panneau pour le bouton Home et l'ajoute à l'est du panneau
        JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 20));
        CustomButton homeButton = new CustomButton("Home");
        p.add(homeButton);
        add(p, BorderLayout.EAST);

        // Crée un panneau pour les jetons et l'ajoute au centre du panneau
        JPanel tokensPanel = new JPanel(new GridLayout(1, 2)); 
        tokensPanel.add(new TokenView(game, "blue")); 
        tokensPanel.add(new TokenView(game, "red")); 
        add(tokensPanel, BorderLayout.CENTER);

        setBackground(Color.LIGHT_GRAY);
        this.setPreferredSize(new Dimension(0, 100));
        
        // Ajoute un écouteur d'action pour le bouton Home
        homeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                homeControl.getPanelFram().removeAll();
                homeControl.getPanelFram().updateUI();
                homeControl.homeWindow();
                homeControl.home();
            }
        });
    }
}
