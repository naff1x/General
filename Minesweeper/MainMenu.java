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
import javax.swing.JOptionPane;
import javax.swing.JOptionPane.*;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

import org.w3c.dom.css.RGBColor;


public class MainMenu extends JFrame implements ActionListener, MouseListener {
    /// Master variables
    static MainMenu base;
    private static final long serialVersionUID = 1L;
    static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    static PrintStream output = System.out;
    /// Remote variables for other classes
    private Game game;                       // TODO: I suspect these variables should be made private. 
    private Instructions instructions;       
    private HighScores highScores;
    //private Font hoverFont = new Font("Sans-Serif", Font.PLAIN, 22);
    /// Colors
    private Color darkerGray = new Color(45, 45, 45);
    private Color darkerWhite = new Color(240,240,240);
    private Color regularGray = new Color(190, 190, 190);
    /// Home Screen variables
    private JButton newGameButton;
    private JButton instructionsButton;
    private JButton highScoresButton;
    static int threshold = 0;
    /// Variables for "newGameButton"
    private UIManager optionPaneManager;
    private int gameVariables; // Used for JOptionPane
    private String height;
    private String width;
    private String bombs;
    private int heightInt;
    private int widthInt;
    private int bombsInt;
    private JTextField widthField;
    private JTextField heightField;
    private JTextField bombsField;
    private Object[] message;
    /// Fonts
    private Font pixelFont;
    private Font fallbackFont = new Font("Sans-Serif", Font.PLAIN, 19);
    /// Images
    private ImageIcon bombIcon;
    public static void main(String[] args) {
        base = new MainMenu("Main Menu");
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

        addFonts();
        addGameButton();
        //addInstructions(); // Inactivated until "Instructions.java" is finished
        addHighScores();
        setLocationRelativeTo(null);
        repaint();
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

    //@Override
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
    //@Override
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
    
    //@Override
    public void mouseReleased(MouseEvent e) {}
    //@Override
    public void mousePressed(MouseEvent e) {}
    //@Override
    public void mouseClicked(MouseEvent e) {}

    //@Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == newGameButton) {
            try {
                widthField = new JTextField();
                heightField = new JTextField();
                bombsField = new JTextField();

                optionPaneManager = new UIManager();
                optionPaneManager.put("OptionPane.background", regularGray);
                optionPaneManager.put("Panel.background", regularGray);

                bombIcon = new ImageIcon("bombIcon.png");

                createOptionPane();

            } catch (Exception ex) {}
        }
        
        else if (e.getSource() == instructionsButton) {
            base.dispose();
            instructions = new Instructions("Instructions");
        } else if (e.getSource() == highScoresButton) {
            base.dispose();
            super.dispose();
            highScores = new HighScores("High Scores");
        }
    } // end of method "actionPerformed"

    public void createOptionPane() {

        Object[] message = {
            "Field size of 81+ required for",
            "registration of your scores!",
            "  ",
            "Enter width of game:", widthField,
            "Enter height of game:", heightField,
            "Enter number of bombs:", bombsField,
        };

        gameVariables = JOptionPane.showConfirmDialog(null, message, "Enter values", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, bombIcon);

        if (gameVariables == JOptionPane.OK_OPTION) {
            width = widthField.getText();
            widthInt = Integer.parseInt(width);
            height = heightField.getText();
            heightInt = Integer.parseInt(height);
            bombs = bombsField.getText();
            bombsInt = Integer.parseInt(bombs);
            
            if (bombsInt > 0 && heightInt > 0 && widthInt > 0) {
                dispose();
                game = new Game("Minesweeper", widthInt, heightInt, bombsInt); 
            }
            if (bombsInt <= 0 || heightInt <= 0 || widthInt <= 0) {
                JOptionPane.showMessageDialog(null, "Please enter a value higher than 0 for all fields!", "What are you doing?", JOptionPane.PLAIN_MESSAGE);
            } 
        } 
        if (gameVariables == JOptionPane.CANCEL_OPTION) {
        } 
        if (gameVariables == JOptionPane.NO_OPTION) {
            JOptionPane.showMessageDialog(null, "Please enter a value for all fields!", "You missed something...", JOptionPane.PLAIN_MESSAGE);
        } 
        if (gameVariables == JOptionPane.CLOSED_OPTION) {
        }
    } // end of method "createOptionPane"

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