package pl.gda.pg.elektronikaodpodstaw.ui;

import pl.gda.pg.elektronikaodpodstaw.main.MainFrame;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Represents a custom button with hover effects for enhanced visual feedback.
 * Extends JButton to provide additional styling and interaction behavior.
 */
public class HoverButton extends JButton {

    /** Indicates whether the button is currently hovered. */
    private boolean isHovered = false;

    /** Indicates whether the button is currently selected. */
    private boolean isSelected = false;

    /**
     * Constructs a HoverButton with default styling and hover behavior.
     * Sets up mouse listeners to handle hover state changes.
     */
    public HoverButton(){
        setFocusPainted(false);
        setContentAreaFilled(false);
        setBorderPainted(false);
        setOpaque(false);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (isEnabled()) {
                    isHovered = true;
                    repaint();
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (isEnabled()) {
                    isHovered = false;
                    repaint();
                }
            }
        });
    }

    /**
     * Constructs a HoverButton with the specified text label.
     * Adjusts styling and behavior based on specific button labels.
     *
     * @param text the label displayed on the button.
     */
    public HoverButton(String text) {
        super(text);
        setFocusPainted(false);
        setContentAreaFilled(true);
        setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        setBackground(MainFrame.getBackgroundTheme());

        if (text.equals("Kontynuuj") && MainFrame.availableLevel == 1) {
            setEnabled(false);
            setForeground(Color.GRAY);
        }

        if (text.equals("Wolna symulacja") && MainFrame.isCompleted.equals("no")) {
            setEnabled(false);
            setForeground(Color.GRAY);
        }

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (isEnabled() && (!getText().equals("Kontynuuj") || MainFrame.availableLevel > 1) ) {
                    isHovered = true;
                    repaint();
                }
                if (isEnabled() && (!getText().equals("Wolna symulacja") || MainFrame.isCompleted.equals("yes")) ) {
                    isHovered = true;
                    repaint();
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (isEnabled() && (!getText().equals("Kontynuuj") || MainFrame.availableLevel > 1) ) {
                    isHovered = false;
                    repaint();
                }
                if (isEnabled() && (!getText().equals("Wolna symulacja") || MainFrame.isCompleted.equals("yes")) ) {
                    isHovered = false;
                    repaint();
                }
            }

        });
    }

    /**
     * Sets the button's selected state and repaints it, helping to indicate which action is selected during the simulation stage.
     *
     * @param selected the new selected state of the button.
     */
    public void setSelected(boolean selected) {
        this.isSelected = selected;
        repaint();
    }

    /**
     * Enables the "Kontynuuj" button and adjusts its styling.
     * Ensures the button is only enabled when specific conditions are met.
     */
    public void enableContinueButton() {
        if (getText().equals("Kontynuuj")) {
            setEnabled(true);
            setForeground(MainFrame.currentTheme.equals("light") ? Color.BLACK : Color.WHITE);
        }
    }

    /**
     * Enables the "Wolna symulacja" button and adjusts its styling.
     * Ensures the button is only enabled when the game is completed.
     */
    public void enableFreeSimButton() {
        if (getText().equals("Wolna symulacja")) {
            setEnabled(true);
            setForeground(MainFrame.currentTheme.equals("light") ? Color.BLACK : Color.WHITE);
        }
    }

    /**
     * Disables the "Kontynuuj" button and adjusts its appearance to indicate it is inactive.
     */
    public void disableContinueButton() {
        if (getText().equals("Kontynuuj")) {
            setEnabled(false);
            setForeground(Color.GRAY);
        }
    }

    /**
     * Disables the "Wolna symulacja" button and adjusts its appearance to indicate it is inactive.
     */
    public void disableFreeSimButton() {
        if (getText().equals("Wolna symulacja")) {
            setEnabled(false);
            setForeground(Color.GRAY);
        }
    }

    /**
     * Paints the button, including custom hover and selection effects.
     * Draws a border around the button when it is hovered or selected.
     *
     * @param g the Graphics context used for rendering.
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (isHovered || isSelected) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setColor(MainFrame.getTextTheme());
            g2.setStroke(new BasicStroke(3));
            g2.drawRect(1, 1, getWidth() - 3, getHeight() - 3);
        }
    }

}
