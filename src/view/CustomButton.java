package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.border.Border;

/**
 * CustomButton class to create a styled JButton with rounded corners.
 */
public class CustomButton extends JButton {
    
    /**
     * Constructor for CustomButton.
     * 
     * @param text the text to display on the button
     */
    public CustomButton(String text) {
        super(text);
        setFocusPainted(false);
        setFont(new Font("Serif", Font.BOLD, 16));
        setBackground(new Color(109, 197, 209));
        setForeground(Color.BLACK);
        setOpaque(false);
        setContentAreaFilled(false);
        
        Border roundedBorder = BorderFactory.createLineBorder(Color.BLACK, 2, true);
        setBorder(roundedBorder);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(100, 40);
    }
}
