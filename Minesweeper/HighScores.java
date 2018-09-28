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
    public HighScores(String name) {
        setTitle(name);
        setResizable(false);
        setLayout(null);
        setContentPane(new JLabel(new ImageIcon("HighScoresTemplate.png")));
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
        //Collections.sort(diffcultyArray);
        output.println("-- DIFFICULTY: " + diffcultyArray);

        timeArray.sort(Comparator.naturalOrder());
        output.println("-- TIME: " + timeArray);

        /// Loops and if-statements for ranking

            for (int i=0; i<scoreArray.size(); i++) { // Go through every object (score entry) [for every object..]

                for (int d=0; d<diffcultyArray.size(); d++) { // [go through every difficulty added...]

                    if (scoreArray.get(i).getDifficulty() == diffcultyArray.get(d)) { // If the current (i) scoreEntry's difficulty equals the current (d) item in difficultyArray...

                        scoreArray.get(i).setRank(d); // Sets the current (i) scoreEntry's rank to ranking in "difficultyArray" (this will be adjusted later)
                    
                        if (i > 0) { // If this IS NOT the first item in "scoreArray", proceed. [one object has already been given a rank])
                        
                            //output.println("s.A. item:" + i + " difficulty: " + scoreArray.get(i).getDifficulty() + " and s.A. i-1: " + scoreArray.get(i-1).getDifficulty());

                            if (scoreArray.get(i).getDifficulty() == scoreArray.get(i-1).getDifficulty()) { // 
        
                                output.println("Difficulties matched!");

                                if (scoreArray.get(i).getTime() < scoreArray.get(i-1).getTime()) { // If current s.A. item has a lower time
                                    output.println("Current s.A. item has lower time!");
                                    scoreArray.get(i).decreaseRankVar();
                                }

                                if (scoreArray.get(i).getTime() > scoreArray.get(i-1).getTime()) { // If current s.A. item has a higher time
                                    output.println("Current s.A. item has higher time!");
                                    scoreArray.get(i).increaseRankVar();
                                }
                            } 
                        }
                    }
                }

                output.println("s.A. loop done! (" + i + ")");
            }

        for (int i=0; i<scoreArray.size(); i++) {
        output.println("-- ENTRY (" + i + ") RANK: "+scoreArray.get(i).getRank());
        output.println("-- ENTRY (" + i + ") DIFFICULTY: " + scoreArray.get(i).getDifficulty());
        output.println("-- ENTRY (" + i + ") TIME: " + scoreArray.get(i).getTime());
        }

        for (int i=0; i<scoreArray.size(); i++) { // This loop finds the top 3 scores and prints out info.
            
            switch (scoreArray.get(i).getRank()) {
                case 0:
                output.println(scoreArray.get(i).getUsername() + " had the best score with " + scoreArray.get(i).getDifficulty() + "% !");
                    break;
                case 1:
                output.println(scoreArray.get(i).getUsername() + " had the 2nd best score with " + scoreArray.get(i).getDifficulty() + "% !");
                    break;
                case 2:
                output.println(scoreArray.get(i).getUsername() + " had the 3rd best score with " + scoreArray.get(i).getDifficulty() + "% !");
                    break;
            }
        }
    } // end of method "rankScores"
} // end of class "game"