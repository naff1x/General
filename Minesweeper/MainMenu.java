import java.io.*;
    import java.util.*;
    import java.awt.Color;
    import java.awt.Dimension;
    import java.awt.FlowLayout;
    import java.awt.Font;
    import java.awt.Insets;
    import java.awt.event.ActionEvent;
    import java.awt.event.ActionListener;
    import java.awt.event.MouseListener;
    import java.awt.Event;
    import java.awt.event.MouseEvent;

import java.util.EventObject;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
    import javax.swing.JButton;
    import javax.swing.JFrame;
    import javax.swing.JLabel;
    import javax.swing.JPanel;
    import javax.swing.SwingConstants;


public class MainMenu extends JFrame implements ActionListener, MouseListener {
    /// General variables
    static MainMenu base; // let's program know about game's existence 
    private static final long serialVersionUID = 1L;
    static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    static PrintStream output = System.out;
    private Font standardFont = new Font("Sans-Serif", Font.PLAIN, 18);
    private Font hoverFont = new Font("Sans-Serif", Font.PLAIN, 22);
    /// Home Screen variables
    private JButton newGameButton;
    //
    
    public static void main(String[] args) {
        base = new MainMenu("Main Menu");
        base.addGameButton();
        base.setLocationRelativeTo(null);
        base.repaint();
    } // end of method "main"

    public MainMenu(String name) { // creates frame for Home Screen
        setTitle(name);
        setResizable(false);
        setContentPane(new JLabel(new ImageIcon("Minesweeper.png")));
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400,500);
        setLocationRelativeTo(null);
        setVisible(true);
    } // end of constructor method "Sweeper"

    public void addGameButton() {
        newGameButton = new JButton("New Game");
        newGameButton.setFont(standardFont);
        newGameButton.setOpaque(false);
        newGameButton.setBorder(BorderFactory.createLineBorder(Color.black));
        newGameButton.setBounds(101, 134, 189, 60);
        newGameButton.addActionListener(this);
        newGameButton.addMouseListener(this);

        add(newGameButton);
    } // end of method "addGameButton"
    
    @Override
    public void mouseEntered(MouseEvent e) {
        if (e.getSource() == newGameButton) {
            //newGameButton.setOpaque(true);
            newGameButton.setFont(hoverFont);
            //newGameButton.setBackground(Color.white);  
        } 
    }  
    @Override
    public void mouseExited(MouseEvent e) {  
        if (e.getSource() == newGameButton) {
            newGameButton.setFont(standardFont);
            //newGameButton.setOpaque(false);  
        } 
    }  
    
    @Override
    public void mouseReleased(MouseEvent e) {}
    @Override
    public void mousePressed(MouseEvent e) {}
    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == newGameButton) {
            base.dispose();
            Game game = new Game("Minesweeper");
        }
    }
} // end of class "Sweeper"