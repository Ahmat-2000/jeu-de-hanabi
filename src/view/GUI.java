package view;

import java.awt.*;
import javax.swing.*;
import controller.HomeController;

/**
 * GUI est une classe qui crée et configure la fenêtre principale de l'application Hanabi.
 */
public class GUI extends JFrame {
    /**
     * Le panneau principal de la fenêtre.
     */
    private JPanel panelFram;

    /**
     * Constructeur pour créer la fenêtre principale de l'application.
     */
    public GUI() {
        super();
        try {
            panelFram = (JPanel) this.getContentPane();
            this.setTitle("Hanabi");
            this.setSize(1100, 900);
            this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            this.setLocationRelativeTo(null); 
            ImageIcon icon = new ImageIcon(getClass().getResource("/content/images/Icon.png"));
            Image newimg = icon.getImage().getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH);  
            icon = new ImageIcon(newimg);
            panelFram.setLayout(null);
            setMinimumSize(new Dimension(700, 730));
            setIconImage(icon.getImage()); 
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Obtient le panneau principal de la fenêtre.
     *
     * @return Le panneau principal.
     */
    public JPanel getPanelFram() {
        return panelFram;
    }

    /**
     * Méthode principale pour démarrer l'application.
     *
     * @param args Les arguments de la ligne de commande.
     */
    public static void main(String[] args) {
        GUI window = new GUI();
        HomeController controller = new HomeController(window);
        controller.home();
        window.setVisible(true);
    }
}
