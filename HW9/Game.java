/**
 * CIS 120 Game HW
 * (c) University of Pennsylvania
 * @version 2.0, Mar 2013
 */

// imports necessary libraries for Java swing
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

/**
 * Game Main class that specifies the frame and widgets of the GUI
 */
public class Game implements Runnable {
    public void run() {
        // NOTE : recall that the 'final' keyword notes inmutability
        // even for local variables.

        // Top-level frame in which game components live
        // Be sure to change "TOP LEVEL FRAME" to the name of your game
        final JFrame frame = new JFrame("Space Invaders");
        frame.setLocation(160, 10);

        // Status panel
        final JPanel status_panel = new JPanel();
        frame.add(status_panel, BorderLayout.SOUTH);
        final JLabel status = new JLabel("Running...");
        status_panel.add(status);

        // Main playing area
        final GameCourt court = new GameCourt(status);
        frame.add(court, BorderLayout.CENTER);

        // Reset button
        final JPanel control_panel = new JPanel();
        frame.add(control_panel, BorderLayout.NORTH);
        //
        // // Note here that when we add an action listener to the reset
        // // button, we define it as an anonymous inner class that is
        // // an instance of ActionListener with its actionPerformed()
        // // method overridden. When the button is pressed,
        // // actionPerformed() will be called.
        final JButton reset = new JButton("Reset");
        reset.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                court.reset();
            }
        });
        // Shop
        final JPanel gameShop = new JPanel();
        gameShop.setLayout(new GridLayout(0, 3));
        // Ship type A button in shop
        JButton buttonShipA = new JButton();
        {
            try {
                Image img = ImageIO.read(new File("SHIPA-icon.png"));
                buttonShipA.setIcon(new ImageIcon(img));
            } catch (IOException ex) {
            }
        }
        ;
        // ActionListener for Ship type A button in shop
        buttonShipA.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (court.square.money < 500) {
                    return;
                } else {
                    court.square.money -= 500;

                    court.square.setLook("shipAA.png");
                    court.square.type = "shipAA.png";
                    court.square.width = court.square.look.getWidth();
                    court.square.height = court.square.look.getHeight();
                }
            }
        });
        // Ship type B button in shop
        JButton buttonShipB = new JButton();
        {
            try {
                Image img1 = ImageIO.read(new File("SHIPB-icon.png"));
                buttonShipB.setIcon(new ImageIcon(img1));
            } catch (IOException ex) {
            }
        }
        ;
        // ActionListener for Ship type B button in shop
        buttonShipB.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (court.square.money < 1000) {
                    return;
                } else {
                    court.square.money -= 1000;

                    court.square.setLook("shipBB.png");
                    court.square.type = "shipBB.png";
                    court.square.width = court.square.look.getWidth();
                    court.square.height = court.square.look.getHeight();
                }
            }
        });
        // Ship type C button in shop
        JButton buttonShipC = new JButton();
        {
            try {
                Image img2 = ImageIO.read(new File("SHIPC-icon.png"));
                buttonShipC.setIcon(new ImageIcon(img2));
            } catch (IOException ex) {
            }
        }
        ;
        // ActionListener for Ship type C button in shop
        buttonShipC.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (court.square.money < 2000) {
                    return;
                } else {
                    court.square.money -= 2000;

                    court.square.setLook("shipCC.png");
                    court.square.type = "shipCC.png";
                    court.square.width = court.square.look.getWidth();
                    court.square.height = court.square.look.getHeight();
                }
            }
        });
        // Adding these purchase buttons to gameShop panel
        gameShop.add(buttonShipA);

        gameShop.add(buttonShipB);

        gameShop.add(buttonShipC);
        // The shop button and its ActionListener
        final JButton shop = new JButton("Shop");
        shop.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!court.inShop) {
                    shop.setText("Back to Game");
                    court.playing = false;
                    court.inShop = true;
                    shop.setFocusable(true);
                    gameShop.setFocusable(true);
                    frame.remove(court);
                    frame.add(gameShop, BorderLayout.CENTER);
                    frame.revalidate();
                    frame.repaint();
                } else {
                    shop.setText("Shop");
                    court.playing = true;
                    court.inShop = false;
                    shop.setFocusable(false);
                    gameShop.setFocusable(false);
                    court.setFocusable(true);
                    frame.remove(gameShop);
                    frame.add(court, BorderLayout.CENTER);
                    frame.revalidate();
                    frame.repaint();
                }
            }
        });
        // Adding reset and shop buttons to the top of the frame
        control_panel.add(reset);
        control_panel.add(shop);
        // Put the frame on the screen
        frame.pack();
        frame.setResizable(false);
        frame.setPreferredSize(new Dimension(1000, 600));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // Start game
        court.reset();
    }

    /*
     * Main method run to start and run the game Initializes the GUI elements
     * specified in Game and runs it IMPORTANT: Do NOT delete! You MUST include
     * this in the final submission of your game.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Game());
    }
}
