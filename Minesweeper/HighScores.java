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

import org.w3c.dom.css.RGBColor;

public class HighScores extends JFrame {

    private static final long serialVersionUID = 1L;
    static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    static PrintStream output = System.out;

    /// Fonts
        private Font pixelFont;
    /// File I/O
        private ArrayList<ScoreEntry> scoreArray;
        private File scoreFile;
        static FileInputStream fileIn;
        static ObjectInputStream objectIn;
        private int id;
        private ScoreEntry entry = null;
        private Boolean objectReturned;
    ///

    public HighScores(String name) {
        setTitle(name);
        setResizable(false);
        setLayout(null);
        setContentPane(new JLabel(new ImageIcon("HighScores.png")));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600,600);
        setLocationRelativeTo(null);
        setVisible(true);

        addFonts();
        readScoreFile();
    } // end of contructor method "game"

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

    public void readScoreFile() {
        scoreFile = new File("scores.ser");

        try {
            fileIn = new FileInputStream(scoreFile);
            //objectIn = new ObjectInputStream(fileIn);
            scoreArray = new ArrayList<ScoreEntry>();
            objectReturned = true;
            /*
            while (true) {
                ScoreEntry entry = (ScoreEntry)objectIn.readObject();
                scoreArray.add(entry);
            }
            */
            while (objectReturned) {
                objectIn = new ObjectInputStream(fileIn);
                ScoreEntry entry = (ScoreEntry)objectIn.readObject();
                if (entry != null) {
                    scoreArray.add(entry);
                } else {
                    objectReturned = false;
                }
            }
            objectIn.close();
            fileIn.close();
        } catch (EOFException ex) {
        } catch (IOException ioe) {
            ioe.printStackTrace();
            output.println("!! - IOException generated in 'HighScores.java'!");
        } catch (ClassNotFoundException cnfe) {
            output.println("!! - A class is missing in 'HighScores.java'!");
        }
        /*
        for (ScoreEntry entry : scoreArray) {
            output.println("Hello");
        } 
        */
        for (int i=0; i<scoreArray.size(); i++) {
            output.println(i);
            output.println("Entry: " + scoreArray.get(i) + " (username: " + scoreArray.get(i).getUsername() + ")" );
        }
    } // end of method "readScoreFile"
} // end of class "game"

class ScoreEntry implements Serializable {

    private static final long serialVersionUID = 1L;
    private String username;
    private int difficulty;
    private int time;

    public ScoreEntry(String usernameInput, int difficultyInput, int timeInput) {
        this.username = usernameInput;
        this.difficulty = difficultyInput;
        this.time = timeInput;
    } // end of constructor method "ScoreEntry"

    public String getUsername() {
        return this.username;
    }

    public int getTime() {
        return this.time;
    }

    public int getDifficulty() {
        return this.difficulty;
    }
} // end of class "ScoreEntry"