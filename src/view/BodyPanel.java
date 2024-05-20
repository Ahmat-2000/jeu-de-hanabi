package view;

import javax.swing.BorderFactory;
import javax.swing.*;

import model.*;
import model.strategies.IA;

import java.awt.*;

/**
 * BodyPanel est un JPanel personnalisé qui affiche différentes vues du jeu, 
 * y compris le bord, les indices, les cartes défaussées et les mains des joueurs.
 */
public class BodyPanel extends JPanel {
    /**
     * Le modèle du jeu.
     */
    private Game game;

    /**
     * Constructeur pour créer un BodyPanel avec le modèle de jeu spécifié.
     *
     * @param game Le modèle de jeu.
     */
    public BodyPanel(Game game) {
        this.game = game;
        setBackground(Color.LIGHT_GRAY);
        setLayout(new BorderLayout(10, 0));

        // BordView
        BordView bordView = new BordView(game);
        bordView.setBorder(BorderFactory.createTitledBorder("Bord"));
        add(bordView, BorderLayout.NORTH);

        // HintView
        HintView hintView = new HintView(game.getActualPlayer(), game.getPlayerHint());
        hintView.setBorder(BorderFactory.createTitledBorder("Hints For Human"));
        JScrollPane HintsScroll = new JScrollPane(hintView);
        HintsScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        HintsScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        add(HintsScroll, BorderLayout.CENTER);

        // DiscardView
        DiscardView discardView = new DiscardView(game);
        discardView.setBorder(BorderFactory.createTitledBorder("Discarded and played cards"));
        add(discardView, BorderLayout.EAST);

        // Player hands
        JPanel playerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        for (Player p : this.game.getPlayers()) {
            HandView playerHand = new HandView(p.getHand());
            if (p instanceof IA) {
                playerHand.setVisibleCard(true);
            }
            playerHand.setBorder(BorderFactory.createTitledBorder(p.getName()));
            playerPanel.add(playerHand);
        }
        JScrollPane playerScroll = new JScrollPane(playerPanel);
        playerScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        playerScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        add(playerScroll, BorderLayout.SOUTH);
    }
}
