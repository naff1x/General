import java.io.*;
    import java.util.*;
    import java.awt.Color;
    import java.awt.Dimension;
    import java.awt.FlowLayout;
    import java.awt.Font;
import java.awt.FontFormatException;
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

import org.w3c.dom.css.RGBColor;


public class MainMenu extends JFrame implements ActionListener, MouseListener {
    /// General variables
    static MainMenu base; // let's program know about game's existence 
    private static final long serialVersionUID = 1L;
    static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    static PrintStream output = System.out;
    private Font standardFont = new Font("Sans-Serif", Font.PLAIN, 19);
    /// Remote variables
    static Game game;
    static Instructions instructions;
    static HighScores highScores;
    //private Font hoverFont = new Font("Sans-Serif", Font.PLAIN, 22);
    /// Colors
    private Color darkerGray = new Color(45, 45, 45);
    private Color darkerWhite = new Color(240,240,240);
    /// Home Screen variables
    private JButton newGameButton;
    private JButton instructionsButton;
    private JButton highScoresButton;
    /// Fonts
    private Font pixelFont;
    
    public static void main(String[] args) {
        base = new MainMenu("Main Menu");
        base.addFonts();
        base.addGameButton();
        base.addInstructions();
        base.addHighScores();
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
        newGameButton.setFont(pixelFont);
        newGameButton.setOpaque(true);
        newGameButton.setBackground(darkerGray);
        newGameButton.setForeground(darkerWhite);
        newGameButton.setBorder(BorderFactory.createLineBorder(darkerGray));
        newGameButton.setBounds(101, 123, 189, 60);
        newGameButton.addActionListener(this);
        newGameButton.addMouseListener(this);

        add(newGameButton);
    } // end of method "addGameButton"

    public void addInstructions() {
        instructionsButton = new JButton("Instructions");
        instructionsButton.setFont(pixelFont);
        instructionsButton.setOpaque(true);
        instructionsButton.setBackground(darkerGray);
        instructionsButton.setForeground(darkerWhite);
        instructionsButton.setBorder(BorderFactory.createLineBorder(darkerGray));
        instructionsButton.setBounds(101, 204, 189, 60);
        instructionsButton.addActionListener(this);
        instructionsButton.addMouseListener(this);

        add(instructionsButton);
    } // end of method "addInstructions"
    
    public void addHighScores() {
        highScoresButton = new JButton("High Scores");
        highScoresButton.setFont(pixelFont);
        highScoresButton.setOpaque(true);
        highScoresButton.setBackground(darkerGray);
        highScoresButton.setForeground(darkerWhite);
        highScoresButton.setBorder(BorderFactory.createLineBorder(darkerGray));
        highScoresButton.setBounds(101, 284, 189, 60);
        highScoresButton.addActionListener(this);
        highScoresButton.addMouseListener(this);

        add(highScoresButton);

    } // end of method "addHighScores"

    @Override
    public void mouseEntered(MouseEvent e) {
        if (e.getSource() == newGameButton) {
            newGameButton.setBackground(darkerWhite);
            newGameButton.setForeground(darkerGray);
        } else if (e.getSource() == instructionsButton) {
            instructionsButton.setBackground(darkerWhite);
            instructionsButton.setForeground(darkerGray);
        } else if (e.getSource() == highScoresButton) {
            highScoresButton.setBackground(darkerWhite);
            highScoresButton.setForeground(darkerGray);
        }
    }  
    @Override
    public void mouseExited(MouseEvent e) {  
        if (e.getSource() == newGameButton) {
            newGameButton.setBackground(darkerGray);
            newGameButton.setForeground(darkerWhite);
        } else if (e.getSource() == instructionsButton) {
            instructionsButton.setBackground(darkerGray);
            instructionsButton.setForeground(darkerWhite);
        } else if (e.getSource() == highScoresButton) {
            highScoresButton.setBackground(darkerGray);
            highScoresButton.setForeground(darkerWhite);
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
            game = new Game("Minesweeper");
        } else if (e.getSource() == instructionsButton) {
            base.dispose();
            instructions = new Instructions("Instructions");
        } else if (e.getSource() == highScoresButton) {
            base.dispose();
            highScores = new HighScores("High Scores");
        }
    } // end of method "actionPerformed"

    public void addFonts() {
        try {
            pixelFont = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream(new File("VCR_OSD_MONO_1.001.ttf"))).deriveFont(Font.PLAIN, 20);   
        } catch (FileNotFoundException e) {
            e.addSuppressed(e);
        } catch (FontFormatException e) {
            e.addSuppressed(e);
        } catch (IOException e) {
            e.printStackTrace();
        }
    } // end of method "addFonts"
} // end of class "Sweeper"