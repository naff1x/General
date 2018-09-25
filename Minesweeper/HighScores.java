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
    /// Other
        private ArrayList<Integer> diffcultyArray;
        private ArrayList<Integer> timeArray;
        private ArrayList<ScoreEntry> top3Array;
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
        rankScores();
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
        
        for (int i=0; i<scoreArray.size(); i++) {
            output.println("Object ID: " +i);
            output.println("Entry: " + scoreArray.get(i) + " (username: " + scoreArray.get(i).getUsername() + ")" );
        }
        
    } // end of method "readScoreFile"

    public void rankScores() {
    
    /// Create arrays for sorting values of difficulty and time
    diffcultyArray = new ArrayList<Integer>();
    timeArray = new ArrayList<Integer>();

    for (int i=0; i<scoreArray.size(); i++) { 
        diffcultyArray.add(scoreArray.get(i).getDifficulty());
        timeArray.add(scoreArray.get(i).getTime());
    }

    diffcultyArray.sort(Comparator.reverseOrder());
    output.println("-- DIFFICULTY: " + diffcultyArray);

    timeArray.sort(Comparator.naturalOrder());
    output.println("-- TIME: " + timeArray);

    /// Time to match the objects to and actually rank them
    for (int i=0; i<scoreArray.size(); i++) {

        for (int d=0; d<diffcultyArray.size(); d++) { // Go through and match difficulties

            //for (int t=0; t<timeArray.size(); t++) { // Go through and match times

                if (diffcultyArray.get(d) == scoreArray.get(i).getDifficulty()) {
                    scoreArray.get(i).setRank(d);
                }
            //}
        }
    }

    for (int i=0; i<scoreArray.size(); i++) {
        output.println("-- RANKS: " + scoreArray.get(i).getRank());
    }

    top3Array = new ArrayList<ScoreEntry>();

    for (int i=0; i<scoreArray.size(); i++) {
        if (scoreArray.get(i).getRank() < 3) {
            top3Array.add(scoreArray.get(i));
            output.println("-- TOP 3 RANKS: " + scoreArray.get(i).getRank());
        }
    }
    //output.println("-- TOP 3: " + top3Array);

    

    /*
    for (int i=0; i<scoreArray.size()-1; i++) {
            scoreArray.get(i).setRank(scoreArray.size());
    
            if (scoreArray.get(i).getDifficulty() > scoreArray.get(i+1).getDifficulty()) {
                scoreArray.get(i).increaseRank(); // Actually decreases variable "rank" but since "rank" is set to the lowest effective rank, it works.
                output.println("Rank reduced! " + scoreArray.get(i).getRank());
            } else {
                output.println("!");
            }
        }
    output.println("-- 'RankScores' finished!");
    */
    } // end of method "rankScores"
} // end of class "game"